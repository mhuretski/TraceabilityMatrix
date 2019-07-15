package tracibility.excel.factory.content.util;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.excel.factory.Matrix;
import tracibility.excel.styles.util.BorderUtil;
import tracibility.excel.styles.util.Hider;

import static tracibility.excel.factory.Matrix.getCell;
import static tracibility.excel.factory.content.util.Cursor.*;
import static tracibility.properties.Constants.SHEET_NAME;

public class Beautifier {

    public static void setBorders(XSSFWorkbook book) {
        getLabelsPositions().forEach(cursor ->
                BorderUtil.setBorderLeftForCell(getCell(LABEL_ROW, cursor), book));
        getLabelsPositions().forEach(cursor ->
                BorderUtil.setBorderLeft(book,
                        new CellRangeAddress(INITIAL_TEST_ROW_POSITION,
                                Cursor.getLastRow(),
                                cursor,
                                Cursor.getLastColumn() + 1)));
        BorderUtil.setBorderBottom(book,
                new CellRangeAddress(Cursor.getLastRow(),
                        Cursor.getLastRow(),
                        INITIAL_COLUMN_CONTENT_POSITION,
                        Cursor.getLastColumn()));
        getLabelsPositions().forEach(cursor ->
                BorderUtil.setBorderLeftForCell(getCell(TEST_ROW, cursor), book));
        BorderUtil.setBorderBottom(book,
                new CellRangeAddress(TEST_ROW,
                        TEST_ROW,
                        INITIAL_COLUMN_CONTENT_POSITION,
                        Cursor.getLastColumn()));
        Hider.hideColumns(book.getSheet(SHEET_NAME));
//        Hider.hideRows(book.getSheet(SHEET_NAME));  //+2Mb size and lags
    }

}
