#include "Table.h" 

std::wstring Trim(std::wstring& s)
{
	CONST std::wstring WHITESPACE = L" \n\r\t\f\v\uFEFF";
	SIZE_T iStart = s.find_first_not_of(WHITESPACE);
	if (iStart == std::string::npos) {
		return L"";
	}
	SIZE_T iEnd = s.find_last_not_of(WHITESPACE);
	return s.substr(iStart, iEnd - iStart + 1);
}

CONST FLOAT CHAR_WIDTH_COEFF = 1.5;
CONST COLORREF TABLE_BRUSH_COLOR = RGB(255, 255, 255);
CONST COLORREF TABLE_PEN_COLOR = RGB(0, 0, 0);
CONST INT TABLE_PEN_WIDTH = 1;
CONST INT TABLE_PEN_TYPE = PS_SOLID;

VOID drawTable(HDC hdc, TTableContent content, int y, int winWidth, int winHeight, int& tableHeight) {
	HFONT font = CreateFont(18, 0, 0, 0, 300, FALSE, FALSE, FALSE,
					DEFAULT_CHARSET, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS,
					DEFAULT_QUALITY, DEFAULT_PITCH, L"Arial Narrow");
	HFONT hOldFont = (HFONT)SelectObject(hdc, font);
	CONST HBRUSH hBr = CreateSolidBrush(TABLE_BRUSH_COLOR);
	CONST HBRUSH hOldBr = (HBRUSH)SelectObject(hdc, hBr);
	CONST HPEN hPn = CreatePen(TABLE_PEN_TYPE, TABLE_PEN_WIDTH, TABLE_PEN_COLOR);
	CONST HPEN hOldPn = (HPEN)SelectObject(hdc, hPn);

	int cellWidth, cellHeight;
	cellWidth = GetCellWidth(content, winWidth);
	cellHeight = GetSuitableCellHeight(hdc, content, cellWidth);

	int rowsCount = content.size();
	int columnsCount = GetNumberOfColumns(content);
	tableHeight = rowsCount * cellHeight;
	if (!rowsCount) {
		Rectangle(hdc, MARGING, y, winWidth - MARGING, winHeight - y);
	}
	else {
		INT xCell = 5;
		INT yCell = y;
		for (UINT uRow = 0; uRow < rowsCount; uRow++) {
			//hghtCell = m_bAlignRows ? m_hghtCellMax : m_ahghtCell.at(uRow);
			for (UINT uCol = 0; uCol < columnsCount; uCol++) {
				Rectangle(hdc, xCell - TABLE_PEN_WIDTH, yCell,
					xCell + cellWidth, yCell + cellHeight + TABLE_PEN_WIDTH);
				RECT rectCell;
				rectCell.left = xCell + TABLE_PEN_WIDTH;
				rectCell.top = yCell + TABLE_PEN_WIDTH;
				rectCell.right = xCell + cellWidth - TABLE_PEN_WIDTH;
				rectCell.bottom = yCell + cellHeight - TABLE_PEN_WIDTH;
				if (content.size() > uRow && content[uRow].size() > uCol) {
					DrawText(hdc, (LPCTSTR)content[uRow][uCol].c_str(), content[uRow][uCol].size(), &rectCell,
						DT_CENTER | DT_WORDBREAK | DT_EDITCONTROL);
				}
				xCell += cellWidth;
			}
			xCell = MARGING;
			yCell += cellHeight;
		}
	}

	SelectObject(hdc, hOldPn);
	DeleteObject(hPn);
	SelectObject(hdc, hOldBr);
	DeleteObject(hBr);
	SelectObject(hdc, hOldFont);
}

int GetCellWidth(TTableContent& content, int winWidth) {
	int columns = GetNumberOfColumns(content);
	return floor((winWidth - 2*MARGING) / columns);
}

int GetNumberOfColumns(TTableContent& content) {
	int columns = 0;
	for (TTableRowContent& row : content)
	{
		if ((INT)row.size() > columns) {
			columns = row.size();
		}
	}
	return columns;
}

int GetSuitableCellHeight(HDC hdc, TTableContent& content, int cellWidth) {
	std::vector<int> allHeights = GetCellsHeights(hdc, content, cellWidth);
	return *std::max_element(allHeights.begin(), allHeights.end());
}

std::vector<int> GetCellsHeights(HDC hdc, TTableContent& content, int cellWidth) {
	TEXTMETRIC txtmtric;
	GetTextMetrics(hdc, &txtmtric);
	CONST INT wdthCellInChars = (INT)std::ceil(cellWidth /
		(txtmtric.tmAveCharWidth * CHAR_WIDTH_COEFF + txtmtric.tmOverhang));
	std::vector<INT> vhghtCell;
	for (TTableRowContent& row : content) {
		int temp = (txtmtric.tmInternalLeading + txtmtric.tmExternalLeading + txtmtric.tmHeight) *
			(INT)std::ceil((double)GetMaxElementLength(row) / wdthCellInChars);
		if (temp) vhghtCell.push_back(temp);
		else vhghtCell.push_back(DEFAULT_HEIGHT);
	}
	return vhghtCell;
}

int GetMaxElementLength(TTableRowContent& row)
{
	INT MaxLength = 0;
	for (std::wstring& sElement : row) {
		if (((INT)sElement.length() > MaxLength)) {
			MaxLength = sElement.length();
		}
	}
	return MaxLength;
}