package tracibility.excel.styles;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Font {

    private final Color color;
    private final XSSFFont hyperlinkFont;
    private final XSSFFont boldFont;

    public Font(XSSFWorkbook book, Color color) {
        this.color = color;
        hyperlinkFont = createHyperlinkFont(book);
        boldFont = createBoldFont(book);
    }

    private XSSFFont createHyperlinkFont(XSSFWorkbook book) {
        XSSFFont linkFont = book.createFont();
        linkFont.setUnderline(XSSFFont.U_SINGLE);
        linkFont.setColor(color.getHyperLink());
        return linkFont;
    }

    private XSSFFont createBoldFont(XSSFWorkbook book) {
        XSSFFont boldFont = book.createFont();
        boldFont.setBold(true);
        return boldFont;
    }

    public XSSFFont getHyperlinkFont() {
        return hyperlinkFont;
    }

    public XSSFFont getBoldFont() {
        return boldFont;
    }

}
