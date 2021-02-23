#include <windows.h>

char szText[] = "Hello world.",
szTitle[] = "Information";

int main()
{
	while (TRUE)
		MessageBox(NULL, szText, szTitle, MB_ICONINFORMATION);
	return EXIT_SUCCESS;
}
/*Просто выведем окошко, в котором через память будем менять текст*/