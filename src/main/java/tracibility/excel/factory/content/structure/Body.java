package tracibility.excel.factory.content.structure;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import tracibility.excel.factory.Preparation;
import tracibility.excel.factory.content.util.Cursor;
import tracibility.excel.styles.Style;

import java.util.Map;

import static tracibility.excel.factory.Matrix.getCell;
import static tracibility.excel.factory.content.util.Cursor.INITIAL_COLUMN_CONTENT_POSITION;
import static tracibility.excel.factory.content.util.Cursor.INITIAL_TEST_ROW_POSITION;

public class Body {

    private Map<Style, XSSFCellStyle> docStyles;

    public Body(Preparation prep) {
        docStyles = prep.getDocStyles();
    }

    public void create() {
        for (int i = INITIAL_TEST_ROW_POSITION; i <= Cursor.getLastRow(); i++) {
            for (int j = INITIAL_COLUMN_CONTENT_POSITION; j <= Cursor.getLastColumn(); j++) {
                getCell(i, j).setCellStyle(docStyles.get(Style.CELL));
            }
        }
    }

}
