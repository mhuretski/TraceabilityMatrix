package tracibility.excel.styles.util;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCol;
import tracibility.excel.factory.Matrix;
import tracibility.excel.factory.content.util.Cursor;

import static tracibility.excel.factory.Matrix.MIN_AMOUNT_OF_COLUMNS;

public class Hider {

    public static void hideColumns(XSSFSheet sheet) {
        CTCol col = sheet.getCTWorksheet().getColsArray(0).addNewCol();
        int minAmount = (Matrix.getNumberOfColumns() <= MIN_AMOUNT_OF_COLUMNS)
                ? Matrix.getNumberOfColumns() + 2
                : Matrix.getNumberOfColumns() + 1;
        col.setMin(minAmount);
        col.setMax(16384);
        col.setHidden(true);
    }

    public static void hideRows(XSSFSheet sheet) {
        int lastRow = 1048576;
        for (int i = Cursor.getLastRow() + 2; i < lastRow; i++) {
            sheet.createRow(i).setZeroHeight(true);
        }
    }

}
