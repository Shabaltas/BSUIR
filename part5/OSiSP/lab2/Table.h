#include <windows.h>
#include <vector>
#include <string>
#include <cmath>
#include <algorithm>
#define MARGING 5
#define DEFAULT_HEIGHT 30
#pragma once
typedef std::vector<std::wstring> TTableRowContent;
typedef std::vector<TTableRowContent> TTableContent;
VOID drawTable(HDC hdc, TTableContent content, int y, int winWidth, int winHeight, int& tableHeight);
std::wstring Trim(std::wstring& s);
int GetNumberOfColumns(TTableContent& content);
int GetCellWidth(TTableContent& content, int winWidth);
int GetSuitableCellHeight(HDC hdc, TTableContent& content, int cellWidth);
std::vector<int> GetCellsHeights(HDC hdc, TTableContent& content, int cellWidth);
int GetMaxElementLength(TTableRowContent& row);
