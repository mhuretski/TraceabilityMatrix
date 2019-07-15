package tracibility.excel.factory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.excel.styles.Style;
import tracibility.excel.styles.util.BorderUtil;
import tracibility.excel.styles.util.PixelUtil;
import tracibility.properties.TestStatus;
import tracibility.util.StructureAnalyzer;

import java.util.Map;

import static tracibility.excel.factory.Matrix.getCell;
import static tracibility.excel.factory.Matrix.getRow;
import static tracibility.excel.factory.content.util.Cursor.*;
import static tracibility.excel.styles.util.Merge.mergeCellsInRow;
import static tracibility.properties.Constants.*;

public class Headers {

    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private Map<Style, XSSFCellStyle> docStyles;
    private Map<TestStatus, XSSFCellStyle> statusStyles;

    public Headers(Preparation prep) {
        book = prep.getBook();
        sheet = prep.getSheet();
        docStyles = prep.getDocStyles();
        statusStyles = prep.getStatusStyles();
    }

    public void create() {
        projectStatus();
        testStatuses();
        setTablePixelSize(Matrix.getNumberOfRows(), Matrix.getNumberOfColumns() + 1);
        traceabilityHeader();
    }

    private void projectStatus() {
        int firstStartColumn = 3;
        int firstFinishColumn = 10;
        int lastStartColumn = 11;
        int lastFinishColumn = 13;
        int firstBodyRow = 2;
        int lastBodyRow = 4;
        String headerValue = "Статистика"
                + ((SPRINT.equals(EMPTY_STRING)) ? EMPTY_STRING : SPACE + SPRINT);
        contentHeader(headerValue, 1, firstStartColumn, firstFinishColumn);
        contentHeader("Прогресс", 1, lastStartColumn, lastFinishColumn);
        contentStatisticsBody("Покрытие требований тестами", 2, firstStartColumn, firstFinishColumn);
        contentStatisticsBody("Успешно пройдено тестов", 3, firstStartColumn, firstFinishColumn);
        contentStatisticsBody("Общая готовность", 4, firstStartColumn, firstFinishColumn);
        contentStatisticsPercentage(2, StructureAnalyzer.getTestCoveragePercent(), lastStartColumn, lastFinishColumn);
        contentStatisticsPercentage(3, StructureAnalyzer.getSuccessfulPassPercent(), lastStartColumn, lastFinishColumn);
        contentStatisticsPercentage(4, StructureAnalyzer.getTotalProgressPercent(), lastStartColumn, lastFinishColumn);
        BorderUtil.setBorder(book, new CellRangeAddress(firstBodyRow, lastBodyRow, firstStartColumn, firstFinishColumn));
        BorderUtil.setBorder(book, new CellRangeAddress(firstBodyRow, lastBodyRow, lastStartColumn, lastFinishColumn));
    }

    private void testStatuses() {
        int firstBodyRow = 7;
        int lastBodyRow = 12;
        int firstColumn = 3;
        int lastColumn = 14;
        contentHeader("Статус теста", 6, firstColumn, lastColumn);
        String[] testStatusText = new String[]{
                "Заблокировано",
                "Провалено",
                "Успешно пройдено",
                "В прогрессе",
                "Не применимо на текущем окружении",
                "Не пройдено"
        };
        setBackgroundForTestStatus(testStatusText, firstBodyRow, lastBodyRow, firstColumn, lastColumn);
        BorderUtil.setBorder(book, new CellRangeAddress(firstBodyRow, lastBodyRow, firstColumn, lastColumn));
    }

    private void traceabilityHeader() {
        String[] values = new String[]{
                "Проект",
                "Компонент",
                "Эпик",
                "История",
                "Требование",
                "Тест"
        };
        for (int i = 0; i < values.length; i++) {
            Cell cell = getCell(INITIAL_ROW_POSITION + i, INITIAL_COLUMN_POSITION);
            cell.setCellStyle(docStyles.get(Style.HEADER_GENERAL));
            cell.setCellValue(values[i]);
        }
        sheet.autoSizeColumn(1);
        //label row height
        getRow(LABEL_ROW).setHeight((short) (getRow(STORY_ROW).getHeight() * 4));
    }

    private void setTablePixelSize(int rowNumber, int columnNumber) {
        for (int i = 0; i < rowNumber; i++) {
            setRowHeight(i, PixelUtil.pixel2HeightPoints(CELL_SIZE));
        }
        for (int i = 0; i < columnNumber; i++) {
            setColumnWidth(i, PixelUtil.pixel2WidthUnits(CELL_SIZE));
        }
    }

    private void setColumnWidth(int columnIndex, short width) {
        sheet.setColumnWidth(columnIndex, width);
    }

    private void setRowHeight(int rowIndex, float height) {
        getRow(rowIndex).setHeightInPoints(height);
    }

    private void contentHeader(String value, int row, int startCell, int endCell) {
        mergeCellsInRow(sheet, row, startCell, endCell);
        getCell(row, startCell).setCellValue(value);
        for (int i = startCell; i <= endCell; i++) {
            getCell(row, i).setCellStyle(docStyles.get(Style.HEADER));
        }
    }

    private void contentStatisticsBody(String value, int row, int startCell, int endCell) {
        mergeCellsInRow(sheet, row, startCell, endCell);
        getCell(row, startCell).setCellValue(value);
        for (int i = startCell; i <= endCell; i++) {
            getCell(row, i).setCellStyle(docStyles.get(Style.STATS_BODY));
        }
    }

    private void contentStatisticsPercentage(int row, double value, int startCell, int endCell) {
        mergeCellsInRow(sheet, row, startCell, endCell);
        getCell(row, startCell).setCellValue(value);
        for (int i = startCell; i <= endCell; i++) {
            getCell(row, i).setCellStyle(docStyles.get(Style.PERCENTAGE));
        }
    }

    private void setBackgroundForTestStatus(String[] testStatusText, int firstBodyRow, int lastBodyRow, int firstColumn, int lastColumn) {
        getCell(firstBodyRow, firstColumn).setCellStyle(statusStyles.get(TestStatus.BLOCKED));
        getCell(firstBodyRow + 1, firstColumn).setCellStyle(statusStyles.get(TestStatus.FAIL));
        getCell(firstBodyRow + 2, firstColumn).setCellStyle(statusStyles.get(TestStatus.PASS));
        getCell(firstBodyRow + 3, firstColumn).setCellStyle(statusStyles.get(TestStatus.WIP));
        getCell(firstBodyRow + 4, firstColumn).setCellStyle(statusStyles.get(TestStatus.NOT_APPLICABLE));
        getCell(firstBodyRow + 5, firstColumn).setCellStyle(statusStyles.get(TestStatus.UNEXECUTED));
        int dashNumber = firstColumn + 1;
        int textNumber = firstColumn + 2;
        String dashValue = "-";
        for (int i = firstBodyRow; i <= lastBodyRow; i++) {
            mergeCellsInRow(sheet, i, textNumber, lastColumn);
            Cell dash = getCell(i, dashNumber);
            dash.setCellStyle(docStyles.get(Style.TEST_STATUS_DASH));
            dash.setCellValue(dashValue);
            Cell cellTooltip = getCell(i, textNumber);
            cellTooltip.setCellStyle(docStyles.get(Style.TEST_STATUS_TEXT));
            cellTooltip.setCellValue(testStatusText[i - firstBodyRow]);
        }
    }


}
