package tracibility.excel.styles;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.properties.TestStatus;

import java.util.HashMap;
import java.util.Map;

public class Styles {

    private final XSSFWorkbook book;
    private final Color color;
    private final Font font;
    private final Map<Style, XSSFCellStyle> bookStyles;
    private final Map<TestStatus, XSSFCellStyle> testStatusStyles;

    public Styles(XSSFWorkbook book, Color color, Font font) {
        this.book = book;
        this.color = color;
        this.font = font;
        bookStyles = new HashMap<>();
        testStatusStyles = new HashMap<>();
    }

    public Map<Style, XSSFCellStyle> getDocStyles() {
        createLeftHeaderStyle();
        createHeaderStyle();
        createHyperHeaderStyle();
        createStatisticsBodyStyle();
        createLabelStyle();
        createEmptyLabelStyle();
        createCellStyle();
        createCountStyle();
        createEmptyCountStyle();
        createTestStyle();
        createPercentStyle();
        createTestStatusTextStyle();
        createTestStatusDashStyle();
        return bookStyles;
    }

    public Map<TestStatus, XSSFCellStyle> getTestStatusStyles() {
        createTestStatusStyles();
        return testStatusStyles;
    }

    private void createLeftHeaderStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getGrey());
        allBordersCellStyle(style, BorderStyle.MEDIUM);
        bookStyles.put(Style.HEADER_GENERAL, style);
    }

    private void createHeaderStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getLightGrey());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        allBordersCellStyle(style, BorderStyle.MEDIUM);
        bookStyles.put(Style.HEADER, style);
    }

    private void createHyperHeaderStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getLightGrey());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font.getHyperlinkFont());
        allBordersCellStyle(style, BorderStyle.MEDIUM);
        bookStyles.put(Style.HEADER_HYPERLINK, style);
    }

    private void createStatisticsBodyStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getWhite());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        allBordersCellStyle(style, BorderStyle.THIN);
        bookStyles.put(Style.STATS_BODY, style);
    }

    private void createLabelStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getLightGrey());
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setRotation((short) 90);
        allBordersCellStyle(style, BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.MEDIUM);
        bookStyles.put(Style.LABEL, style);
    }

    private void createEmptyLabelStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getGrey());
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setRotation((short) 90);
        allBordersCellStyle(style, BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.MEDIUM);
        bookStyles.put(Style.EMPTY_LABEL, style);
    }

    private void createCellStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getWhite());
        allBordersCellStyle(style, BorderStyle.THIN);
        bookStyles.put(Style.CELL, style);
    }

    private void createCountStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(color.getWhite());
        allBordersCellStyle(style, BorderStyle.THIN);
        bookStyles.put(Style.COUNT, style);
    }

    private void createEmptyCountStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(color.getGrey());
        allBordersCellStyle(style, BorderStyle.THIN);
        bookStyles.put(Style.EMPTY_COUNT, style);
    }

    private void createTestStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getLightGrey());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font.getHyperlinkFont());
        allBordersCellStyle(style, BorderStyle.MEDIUM);
        bookStyles.put(Style.TEST, style);
    }

    private void createPercentStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setDataFormat(book.createDataFormat().getFormat("0.00%"));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getWhite());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font.getBoldFont());
        allBordersCellStyle(style, BorderStyle.THIN);
        bookStyles.put(Style.PERCENTAGE, style);
    }

    private void createTestStatusTextStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getWhite());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        bookStyles.put(Style.TEST_STATUS_TEXT, style);
    }

    private void createTestStatusDashStyle() {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color.getWhite());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        bookStyles.put(Style.TEST_STATUS_DASH, style);
    }

    private void createTestStatusStyles() {
        createTestStatusStyle(TestStatus.BLOCKED, color.getBlocked());
        createTestStatusStyle(TestStatus.FAIL, color.getFail());
        createTestStatusStyle(TestStatus.PASS, color.getPass());
        createTestStatusStyle(TestStatus.WIP, color.getWip());
        createTestStatusStyle(TestStatus.NOT_APPLICABLE, color.getNotApplicable());
        createTestStatusStyle(TestStatus.UNEXECUTED, color.getUnexecuted());
        createTestStatusStyle(TestStatus.DEFAULT, color.getWhite());
    }

    private void createTestStatusStyle(TestStatus testStatus, XSSFColor color) {
        XSSFCellStyle style = book.createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color);
        allBordersCellStyle(style, BorderStyle.THIN);
        testStatusStyles.put(testStatus, style);
    }

    private void allBordersCellStyle(XSSFCellStyle style, BorderStyle borderStyle) {
        style.setBorderBottom(borderStyle);
        style.setBorderTop(borderStyle);
        style.setBorderLeft(borderStyle);
        style.setBorderRight(borderStyle);
    }

}
