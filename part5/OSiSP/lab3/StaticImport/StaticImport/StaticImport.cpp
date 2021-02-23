#include <windows.h>
#include <tchar.h> 
#include <tlhelp32.h>
#include <stdio.h>
#pragma comment(lib, "Dllka.lib")
#define NAME_LIB "Dllka.dll"
#define NAME_REPLACER "DllWithReplacer.dll"
#define SUM "Sum"
#define REPLACER "destroyThemAll"
#define STRLEN(x) (sizeof(x)/sizeof(TCHAR))

char PATH_TO_DLL_FOR_INJECTION[] = "DllForInject.dll";
char PROC_NAME[] = "MemoryRewriterTestProject.exe";

__declspec(dllimport) int Sum(int, int);

typedef int(*Summ)(int, int);
typedef int(*Replace)();

typedef HMODULE(WINAPI* LPLoadLibrary)(LPCSTR);
typedef HMODULE(WINAPI* LPGetProcAddress)(HMODULE, LPCSTR);

void injectLibrary();

int main()
{
	HMODULE hDll = LoadLibrary(NAME_LIB);
	if (!hDll)
		ExitProcess(1);

	Summ Sum1 = (Summ)GetProcAddress(hDll, SUM);

	int iValSum = 0;
	int staticSumRes = Sum(2, 3);

	if (Sum1)
	{
		iValSum = Sum1(1, 2);
	}

	TCHAR buf[300]; // where you put result
	sprintf_s(buf, TEXT("%d\n"), iValSum);

	TCHAR buf2[300]; // where you put result
	sprintf_s(buf2, TEXT("%d\n"), staticSumRes);

	HANDLE hStdout = GetStdHandle(STD_OUTPUT_HANDLE);
	HANDLE hStdin = GetStdHandle(STD_INPUT_HANDLE);
	DWORD dwCount = 300;

	WriteConsole(hStdout, &buf, lstrlen(buf), &dwCount, NULL);
	WriteConsole(hStdout, &buf2, lstrlen(buf2), &dwCount, NULL);
	FreeLibrary(hDll);
	//Replacer
	injectLibrary();
	HMODULE hReplacer = LoadLibrary(NAME_REPLACER);
	if (!hReplacer)
		ExitProcess(1);

	Replace replace = (Replace)GetProcAddress(hReplacer, REPLACER);

	replace();

	FreeLibrary(hReplacer);

	ExitProcess(0);
	
}

DWORD fGetPID(char* szProcessName)
{
	PROCESSENTRY32 pe = { sizeof(PROCESSENTRY32) };
	HANDLE ss;
	DWORD dwRet = 0;

	ss = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);

	if (ss) {
		if (Process32First(ss, &pe))
			while (Process32Next(ss, &pe)) {
				if (!strcmp(pe.szExeFile, szProcessName)) {
					dwRet = pe.th32ProcessID;
					break;
				}
			} CloseHandle(ss);
	}
	return dwRet;
}

void injectLibrary() {
	HMODULE hdll = LoadLibraryA("Kernel32.dll");

	LPLoadLibrary LoadLibraryA = (LPLoadLibrary)GetProcAddress(hdll, "LoadLibraryA");

	HANDLE hProc = OpenProcess(PROCESS_VM_READ | PROCESS_VM_OPERATION | PROCESS_VM_WRITE |
		PROCESS_CREATE_THREAD | PROCESS_CREATE_PROCESS,
		FALSE,
		fGetPID(PROC_NAME));
	printf("hProc: %d\n", hProc);
	LPVOID path = VirtualAllocEx(hProc, NULL, strlen(PATH_TO_DLL_FOR_INJECTION) + 1, MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
	
	int er = WriteProcessMemory(hProc, path, PATH_TO_DLL_FOR_INJECTION, strlen(PATH_TO_DLL_FOR_INJECTION) + 1, NULL);
	printf("\er: %d\n", er);
	DWORD threadId;

	HANDLE hThread = CreateRemoteThread(hProc, NULL, NULL, (LPTHREAD_START_ROUTINE)LoadLibraryA, (LPVOID)path, NULL, &threadId);

	if (hThread == NULL)
	{
		printf("error\n");
	}
	else {
		printf("hThread: %d\n", hThread);
	}
	FreeLibrary(hdll);
	CloseHandle(hProc);
}


