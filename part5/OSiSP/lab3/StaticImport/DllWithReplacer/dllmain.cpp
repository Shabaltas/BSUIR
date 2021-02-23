// dllmain.cpp : Определяет точку входа для приложения DLL.
#include "pch.h"
#include <stdio.h>
#include <windows.h>
#include <tlhelp32.h>
#include <conio.h>
#include <stdlib.h>
char PROC_NAME[] = "Check.exe";
#define MAX_READ 128

#define EXPORT __declspec(dllexport)

int EXPORT destroyThemAll();

BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
                     )
{
    switch (ul_reason_for_call)
    {
    case DLL_PROCESS_ATTACH:
    case DLL_THREAD_ATTACH:
    case DLL_THREAD_DETACH:
    case DLL_PROCESS_DETACH:
        break;
    }
    return TRUE;
}

int fMatchCheck(char* mainstr, int mainstrLen, char* checkstr, int checkstrLen)
{
	/*
	Проверка наличия подстроки в строке.
	При этом под "строкой" подразумевается
	просто последовательность байт.
	*/
	BOOL fmcret = TRUE;
	int x, y;

	for (x = 0; x < mainstrLen; x++) {
		fmcret = TRUE;

		for (y = 0; y < checkstrLen; y++) {
			if (checkstr[y] != mainstr[x + y]) {
				fmcret = FALSE;
				break;
			}
		}

		if (fmcret)
			return x + checkstrLen;
	}
	return -1;
}

char* replaceMem(char* buff, size_t buffLen, int from, int to, char* replaceBuff, size_t replaceLen)
{

	size_t ourSize = buffLen - (to - from + 1) + replaceLen - 1;
	char* ret = (char*)malloc(ourSize);
	int retPos = 0;
	for (int pos = 0; pos < from; pos++) {
		ret[retPos++] = buff[pos];
	}
	for (int i = 0; i < replaceLen - 1; i++) {
		ret[retPos++] = replaceBuff[i];
	}
	for (int pos = to; pos < buffLen; pos++) {
		ret[retPos++] = buff[pos];
	}
	return ret;
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

BOOL DoRtlAdjustPrivilege()
{
	/*
	Важная функция. Получаем привилегии дебаггера.
	Именно это позволит нам получить нужную информацию
	о доступности памяти.
	*/
#define SE_DEBUG_PRIVILEGE 20L
#define AdjustCurrentProcess 0
	BOOL bPrev = FALSE;
	LONG(WINAPI * RtlAdjustPrivilege)(DWORD, BOOL, INT, PBOOL);
	*(FARPROC*)& RtlAdjustPrivilege = GetProcAddress(GetModuleHandle("ntdll.dll"), "RtlAdjustPrivilege");
	if (!RtlAdjustPrivilege) return FALSE;
	RtlAdjustPrivilege(SE_DEBUG_PRIVILEGE, TRUE, AdjustCurrentProcess, &bPrev);
	return TRUE;
}

int EXPORT destroyThemAll()
{
	/*** VARIABLES ***/
	HANDLE hProc;

	MEMORY_BASIC_INFORMATION mbi;
	SYSTEM_INFO msi;
	ZeroMemory(&mbi, sizeof(mbi));
	GetSystemInfo(&msi);
	/*
	Получаем информацию о памяти в текущей системе.
	*/
	DWORD dwWrite = 0;
	DWORD dwRead = 0;

	char* lpData = (char*)GlobalAlloc(GMEM_FIXED, MAX_READ),
		lpOrig[] = "Information", // что ищем
		lpReplacement[] = "Noformation"; // на что меняем

	int x, at;
	/*****************/

	if (!lpData)
		return -1;

	ZeroMemory(lpData, MAX_READ);

	// открываем процесс
	do {
		hProc = OpenProcess(PROCESS_VM_READ | PROCESS_TRUST_LABEL_SECURITY_INFORMATION | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION,
			FALSE,
			fGetPID(PROC_NAME));
		if (!hProc) {
			Sleep(500);
			puts("Cant open process!\n"
				"Press any key to retry.\n");
			_getch();
		}
	} while (!hProc);
	printf("hProc: %d\n", hProc);
	if (DoRtlAdjustPrivilege()) {
		/*
		Привилегии отладчика для работы с памятью.
		*/

		puts("Process opened sucessfully\n"
			"Scanning memory...\n");

		for (LPBYTE lpAddress = (LPBYTE)msi.lpMinimumApplicationAddress;
			lpAddress <= (LPBYTE)msi.lpMaximumApplicationAddress;
			lpAddress += mbi.RegionSize) {
			/*
			Этот цикл отвечает как раз за то, что наша программа не совершит
			лишних действий. Память в Windows в процессе делится на "регионы".
			У каждого региона свой уровень доступа: к какому-то доступ запрещен,
			какой-то можно только прочитать. Нам нужны регионы доступные для записи.
			Это позволит в разы ускорить работу поиска по памяти и избежать ошибок
			записи в память. Именно так работает ArtMoney.
			*/

			if (VirtualQueryEx(hProc, lpAddress, &mbi, sizeof(mbi))) {
				/*
				Узнаем о текущем регионе памяти.
				*/

				if ((mbi.Protect & PAGE_READWRITE) || (mbi.Protect & PAGE_WRITECOPY)) {
					/*
					Если он доступен для записи, работаем с ним.
					*/

					for (lpAddress;
						lpAddress < (lpAddress + 0x00000100);
						lpAddress += 0x00000100) {
						/*
						Проходим по адресам указателей в памяти чужого процесса от начала, до конца региона
						и проверяем, не в них ли строка поиска.
						*/

						dwRead = 0;
						if (ReadProcessMemory(hProc,
							(LPCVOID)lpAddress,
							lpData,
							MAX_READ,
							&dwRead) == TRUE) {
							/*
							Читаем по 128 байт из памяти чужого процесса от начала
							и проверяем, не в них ли строка поиска.
							*/

							if (fMatchCheck(lpData, dwRead, lpOrig, sizeof(lpOrig) - 1) != -1) {
								/*Нашли, сообщим об успехе и поменяем в чужом процессе искомую строку на нашу.*/
								printf("MEMORY ADDRESS: 0x00%x\n"
									"DATA:\n", lpAddress);
								for (x = 0; x < dwRead; x++) {
									printf("%c", lpData[x]);
								} puts("\n");

								at = fMatchCheck(lpData,
									dwRead,
									lpOrig,
									sizeof(lpOrig) - 1);

								if (at != -1) {
									at -= sizeof(lpOrig) - 1;
									//Если откат, менять всё на lpOrig размер
									lpData = replaceMem(lpData,
										dwRead,
										at,
										at + sizeof(lpOrig) - 1,
										lpReplacement,
										/*sizeof(lpReplacement)-1*/sizeof(lpReplacement));

									puts("REPLACEMENT DATA:");
									for (x = 0; x < dwRead - sizeof(lpOrig) - 1 + sizeof(lpReplacement) - 1; x++) {
										printf("%c", lpData[x]);
									} puts("\n");

									puts("Replacing memory...");
									if (WriteProcessMemory(hProc,
										(LPVOID)lpAddress,
										lpData,
										/*dwRead-sizeof(lpOrig)-1+sizeof(lpReplacement)-1*/dwRead + sizeof(lpReplacement) - sizeof(lpOrig),
										&dwWrite)) {
										printf("%d\n", dwWrite);
										puts("Success.\n");
									}
									else puts("Error1.\n");
								}
								else puts("Error2.\n");

							}

						}
					}

				}
				else puts("Error3.\n");
			}
			else puts("Error4.\n");
		}
	}
	else puts("Error5.\n");

	// // // // //
	// Cleanup
	if (hProc)
		CloseHandle(hProc);
	if (lpData)
		GlobalFree(lpData);
	///////////////

	puts("Done. Press any key to quit...");
	return _getch();
}

