package tracibility.excel.factory;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.excel.styles.Color;
import tracibility.excel.styles.Font;
import tracibility.excel.styles.Style;
import tracibility.excel.styles.Styles;
import tracibility.properties.TestStatus;

import java.util.Map;

import static tracibility.properties.Constants.SHEET_NAME;

public class Preparation {

    private final XSSFWorkbook book;
    private final XSSFSheet sheet;
    private final Color color;
    private final Font font;
    private final Map<Style, XSSFCellStyle> docStyles;
    private final Map<TestStatus, XSSFCellStyle> statusStyles;

    public Preparation() {
        book = new XSSFWorkbook();
        sheet = book.createSheet(SHEET_NAME);
        color = new Color(book);
        font = new Font(book, color);
        Styles style = new Styles(book, color, font);
        docStyles = style.getDocStyles();
        statusStyles = style.getTestStatusStyles();
    }

    public XSSFWorkbook getBook() {
        return book;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public Color getColor() {
        return color;
    }

    public Font getFont() {
        return font;
    }

    public Map<Style, XSSFCellStyle> getDocStyles() {
        return docStyles;
    }

    public Map<TestStatus, XSSFCellStyle> getStatusStyles() {
        return statusStyles;
    }

}
