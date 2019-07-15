package tracibility.excel.styles.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class Merge {

    public static void mergeCells(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (!(firstRow == lastRow && firstCol == lastCol))
            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    public static void mergeCellsInRow(Sheet sheet, int row, int firstCol, int lastCol) {
        mergeCells(sheet, row, row, firstCol, lastCol);
    }

}
