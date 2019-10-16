#include "framework.h"
#include "lab2.h"
#include <string>
#include <windows.h>
#include "Commdlg.h"
#include <fstream>
#include <sstream>
#include <codecvt>
#include "Table.h"


#define VSCROLLBAR_STEP 5
#define MAX_LOADSTRING 100

OPENFILENAME ShowOpenTableFileDialog(HWND hWnd);
TTableContent GetFileContent(std::wstring sPath, WCHAR cDelimiter);
VOID OnTryVerticalScroll(HWND hWnd, WPARAM wParam);
VOID OnTryPaint(HWND hWnd);
INT hghtWnd, wdthWnd, wdthTable;

// Global Variables:
HINSTANCE hInst;                                // current instance
WCHAR szTitle[MAX_LOADSTRING];                  // The title bar text
WCHAR szWindowClass[MAX_LOADSTRING];            // the main window class name
TTableContent content;
HFONT font;
int winWidth, winHeight, tableHeight;
// Forward declarations of functions included in this code module:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    // TODO: Place code here.

    // Initialize global strings
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_LAB2, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Perform application initialization:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_LAB2));

    MSG msg;

    // Main message loop:
    while (GetMessage(&msg, nullptr, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int) msg.wParam;
}



//
//  FUNCTION: MyRegisterClass()
//
//  PURPOSE: Registers the window class.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;

    wcex.cbSize = sizeof(WNDCLASSEX);

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_LAB2));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_LAB2);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

    return RegisterClassExW(&wcex);
}

//
//   FUNCTION: InitInstance(HINSTANCE, int)
//
//   PURPOSE: Saves instance handle and creates main window
//
//   COMMENTS:
//
//        In this function, we save the instance handle in a global variable and
//        create and display the main program window.
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance; // Store instance handle in our global variable

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW | WS_VSCROLL,
      CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   //load file content
   OPENFILENAME openFileName = ShowOpenTableFileDialog(hWnd);
   BOOLEAN res = GetOpenFileName(&openFileName);
   if (res) {
	   try {
		   content = GetFileContent(openFileName.lpstrFile, ';');
	   }
	   catch (std::exception* e) {
		   MessageBox(hWnd, L"File wasn't opened", L"Failure", MB_OK | MB_ICONERROR);
		   delete e;
		   return TRUE;
	   }
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

//
//  FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  PURPOSE: Processes messages for the main window.
//
//  WM_COMMAND  - process the application menu
//  WM_PAINT    - Paint the main window
//  WM_DESTROY  - post a quit message and return
//
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    switch (message)
    {
	case WM_CREATE:
		ShowScrollBar(hWnd, SB_VERT, TRUE);
		break;
	case WM_SIZE:
	{
		winWidth = LOWORD(lParam);
		winHeight = HIWORD(lParam);
		break;
	}
	case WM_VSCROLL:
		OnTryVerticalScroll(hWnd, wParam);
		break;
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            switch (wmId)
            {
            case IDM_ABOUT:
                DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
                break;
            case IDM_EXIT:
                DestroyWindow(hWnd);
                break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        OnTryPaint(hWnd);
        break;
    case WM_DESTROY:
		DeleteObject(font);
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}

// Message handler for about box.
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    UNREFERENCED_PARAMETER(lParam);
    switch (message)
    {
    case WM_INITDIALOG:
        return (INT_PTR)TRUE;

    case WM_COMMAND:
        if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
        {
            EndDialog(hDlg, LOWORD(wParam));
            return (INT_PTR)TRUE;
        }
        break;
    }
    return (INT_PTR)FALSE;
}

OPENFILENAME ShowOpenTableFileDialog(HWND hWnd)
{
	OPENFILENAME ofn;
	WCHAR szFileName[MAX_PATH];

	ofn = { 0 };
	ofn.lStructSize = sizeof(OPENFILENAME);
	ofn.hwndOwner = hWnd;
	ofn.lpstrFilter = L"Text Files (*.txt)\0*.txt\0";
	ofn.lpstrFile = NULL;
	ofn.nMaxFile = MAX_PATH;
	ofn.Flags = OFN_EXPLORER | OFN_FILEMUSTEXIST | OFN_HIDEREADONLY;
	ofn.lpstrDefExt = L"txt";
	ofn.lpstrFile = szFileName;
	ofn.lpstrFile[0] = '\0';
	return ofn;
}

TTableContent GetFileContent(std::wstring path, WCHAR cDelimiter)
{
	
	std::wifstream ifs;
	std::locale loc = ifs.getloc();
	ifs.imbue(std::locale(ifs.getloc(), new std::codecvt_utf8<WCHAR>));

	TTableContent content;
	std::wstring line;
	ifs.open("sample.txt", std::ifstream::in);
	if (ifs.is_open()) {
		while (getline(ifs, line)) {
			// using printf() in all tests for consistency
			printf("%s", line.c_str());
			if (line.size()) {
				std::wistringstream iss(line);
				TTableRowContent rowContent;
				std::wstring word;
				while (std::getline(iss, word, cDelimiter)) {
					rowContent.push_back(Trim(word));
				}
				content.push_back(rowContent);
			}
		}
		ifs.close();
	}
	return content;
}

VOID OnTryVerticalScroll(HWND hWnd, WPARAM wParam)
{
	static int yScrollBarPos = 0;
	SCROLLINFO si;
	si.cbSize = sizeof(SCROLLINFO);
	si.fMask = SIF_ALL;
	GetScrollInfo(hWnd, SB_VERT, &si);

	switch (LOWORD(wParam)) {
	case SB_LINEUP:
	case SB_PAGEUP:
		si.nPos -= VSCROLLBAR_STEP;
		break;
	case SB_LINEDOWN:
	case SB_PAGEDOWN:
		si.nPos += VSCROLLBAR_STEP;
		break;
	case SB_THUMBTRACK:
		si.nPos = si.nTrackPos;
		break;
	}

	si.fMask = SIF_POS;
	SetScrollInfo(hWnd, SB_VERT, &si, TRUE);
	GetScrollInfo(hWnd, SB_VERT, &si);

	if (si.nPos != yScrollBarPos)
	{
		ScrollWindow(hWnd, 0, yScrollBarPos - si.nPos, NULL, NULL);
		InvalidateRect(hWnd, NULL, TRUE);
		UpdateWindow(hWnd);
		yScrollBarPos = si.nPos;
	}
}

VOID OnTryPaint(HWND hWnd)
{
	PAINTSTRUCT ps;
	HDC hdc = BeginPaint(hWnd, &ps);

	RECT wndRect;
	GetClientRect(hWnd, &wndRect);
	hghtWnd = wndRect.bottom - wndRect.top;
	wdthWnd = wndRect.right - wndRect.left;
	wdthTable = wdthWnd - 2 * MARGING;

	SCROLLINFO si;
	si.cbSize = sizeof(SCROLLINFO);
	si.fMask = SIF_ALL;

	GetScrollInfo(hWnd, SB_VERT, &si);
	drawTable(hdc, content, MARGING - si.nPos, winWidth, winHeight, tableHeight);

	si.cbSize = sizeof(SCROLLINFO);
	si.fMask = SIF_RANGE;
	si.nMin = 0;
	si.nMax = tableHeight + 2 * MARGING - hghtWnd;
	SetScrollRange(hWnd, SB_VERT,si.nMin, si.nMax, TRUE);
	EndPaint(hWnd, &ps);
}