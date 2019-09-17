// lab1.cpp : Defines the entry point for the application.
//

#include "framework.h"
#include "lab1.h"

#define MAX_LOADSTRING 100
#define TIMER_ELAPSE 5
#define SHAPE_WIDTH 150
#define SHAPE_HEIGHT 90

// Global Variables:
HINSTANCE hInst;                                // current instance
WCHAR szTitle[MAX_LOADSTRING];                  // The title bar text
WCHAR szWindowClass[MAX_LOADSTRING];            // the main window class name

// Forward declarations of functions included in this code module:
ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);

HPEN hpen1 = CreatePen(PS_SOLID, 2, RGB(255, 255, 0)),
hpen2 = CreatePen(PS_SOLID, 2, RGB(0, 255, 0));
HBRUSH hbrush1 = CreateHatchBrush(HS_DIAGCROSS, RGB(255, 255, 0)),
hbrush2 = CreateSolidBrush(RGB(0, 255, 0));

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
    LoadStringW(hInstance, IDC_LAB1, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    // Perform application initialization:
    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_LAB1));

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
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_LAB1));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = (HBRUSH)(COLOR_WINDOW+1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_LAB1);
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

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_OVERLAPPEDWINDOW,
      CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
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
	static BOOLEAN decX = false, decY = false;
	static int x = 0, y = 0, winWidth, winHeight, xEl = 0, yEl = 0;
	static int caption, menu, border;
	static HDC memBit, hdc;
	static HBITMAP hBitmap;
	static BITMAP bm;
    switch (message)
    {
	case WM_CREATE:
		SetTimer(hWnd, 1, TIMER_ELAPSE, NULL);
		//hBitmap = (HBITMAP)LoadBitmap(NULL, MAKEINTRESOURCEW(IDB_BITMAP2));
		hBitmap = (HBITMAP)LoadImage(NULL, _T("Pikachu1.bmp"), IMAGE_BITMAP,0, 0, LR_LOADFROMFILE | LR_CREATEDIBSECTION);
		GetObject(hBitmap, sizeof(bm), &bm);
		hdc = GetDC(hWnd);
		memBit = CreateCompatibleDC(hdc);
		SelectObject(memBit, hBitmap);
		ReleaseDC(hWnd, hdc);
		break;
	case WM_SIZE:
	{
		winWidth = LOWORD(lParam);
		winHeight = HIWORD(lParam);
		break;
	}
	case WM_TIMER:
	{
		if (decX) xEl--;
		else xEl++;
		if (decY) yEl--;
		else yEl++;
		InvalidateRect(hWnd, NULL, TRUE);
		break;
	}
    case WM_COMMAND:
        {
            int wmId = LOWORD(wParam);
            // Parse the menu selections:
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
	case WM_MOUSEWHEEL:
	{
		int delta = (short)HIWORD(wParam);
		if (LOWORD(wParam) == MK_SHIFT) {
			x += delta;
		}
		else {
			y += delta;
		}
		InvalidateRect(hWnd, NULL, TRUE);
		break;
	}
	case WM_KEYDOWN:
		{
			switch (wParam) {
			case VK_UP:
				y -= LOWORD(lParam);
				break;
			case VK_LEFT:
				x -= LOWORD(lParam);
				break;
			case VK_DOWN:
				y += LOWORD(lParam);
				break;
			case VK_RIGHT:
				x += LOWORD(lParam);
				break;
			}
			InvalidateRect(hWnd, NULL, TRUE);
			break;
		}
    case WM_PAINT:
        {
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
			SelectObject(hdc, hpen1);
			SelectObject(hdc, hbrush1);
			/*
			if (x < 0) x = winWidth - SHAPE_WIDTH;
			if (y < 0) y = winHeight - SHAPE_HEIGHT;
			x %= winWidth;
			y %= winHeight;
			*/
			if (x < 0) x = 0;
			else if (x + SHAPE_WIDTH > winWidth) x = winWidth - SHAPE_WIDTH;
			if (y < 0) y = 0;
			else if (y + SHAPE_HEIGHT > winHeight) y = winHeight - SHAPE_HEIGHT;
			Rectangle(hdc, x , y, x + SHAPE_WIDTH, y + SHAPE_HEIGHT);

			SelectObject(hdc, hpen2);
			SelectObject(hdc, hbrush2);
			if (xEl + bm.bmWidth == winWidth) decX = true;
			if (xEl == 0) decX = false;
			if (yEl + bm.bmHeight == winHeight) decY = true;
			if (yEl == 0) decY = false;
			//Ellipse(hdc, xEl, yEl, xEl + SHAPE_WIDTH, yEl + SHAPE_HEIGHT);

			TransparentBlt(hdc, xEl, yEl, bm.bmWidth, bm.bmHeight, memBit, 0, 0, bm.bmWidth, bm.bmHeight, RGB(255, 255, 255));
			//BitBlt(hdc, xEl, yEl, bm.bmWidth, bm.bmHeight, memBit, 0, 0, SRCCOPY);
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
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
