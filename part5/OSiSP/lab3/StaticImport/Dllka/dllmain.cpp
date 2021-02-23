// dllmain.cpp : Определяет точку входа для приложения DLL.
#include "pch.h"

#define EXPORT __declspec(dllexport)

int EXPORT Sum(int i, int j);

BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
                     )
{
    return TRUE;
}

int EXPORT Sum(int i, int j) {
	return i + j;
}
