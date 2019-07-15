package tracibility.excel.factory.content.structure;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.excel.factory.Matrix;
import tracibility.excel.factory.Preparation;
import tracibility.excel.factory.content.util.Cursor;
import tracibility.excel.styles.Color;
import tracibility.excel.styles.Style;
import tracibility.excel.styles.util.HyperLink;
import tracibility.properties.TestStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static tracibility.excel.factory.Matrix.getCell;
import static tracibility.excel.factory.content.util.Cursor.*;
import static tracibility.jira.util.Parser.STORIES_TO_TESTS;
import static tracibility.properties.Constants.EMPTY_STRING;

public class Content {

    private XSSFWorkbook book;
    private Color color;
    private Map<Style, XSSFCellStyle> docStyles;
    private Map<TestStatus, XSSFCellStyle> statusStyles;

    public Content(Preparation prep) {
        book = prep.getBook();
        color = prep.getColor();
        docStyles = prep.getDocStyles();
        statusStyles = prep.getStatusStyles();
    }

    public void createAndSetDependencies() {
        System.out.println("Plain Vertical Structure : " + Matrix.getPlainVerticalStructure());
        Matrix.getHorizontalStructure().forEach((test, status) -> {
            setHeaderValue(test, Cursor.getTestRow());
            List<String> labels = Matrix.getPlainVerticalStructure()
                    .entrySet()
                    .stream()
                    .map(entry ->
                            (entry.getValue()
                                    .stream()
                                    .anyMatch(testId -> testId.equals(test)))
                                    ? entry.getKey()
                                    : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!labels.isEmpty()) {
                String storyValue = null;
                for (int i = INITIAL_COLUMN_CONTENT_POSITION; i <= Cursor.getLastColumn(); i++) {
                    String cellValue = getCell(STORY_ROW, i).getStringCellValue();
                    if (cellValue != null && !cellValue.equals(EMPTY_STRING)) {
                        storyValue = cellValue;
                    }
                    Cell labelCell = getCell(LABEL_ROW, i);
                    String finalStoryValue = storyValue;
                    labels.forEach(label -> {
                        boolean labelInTheRightStory = false;
                        if (STORIES_TO_TESTS.containsKey(finalStoryValue)) {
                            labelInTheRightStory = STORIES_TO_TESTS.get(finalStoryValue)
                                    .stream()
                                    .filter(val -> val.equals(test))
                                    .findAny()
                                    .orElse(null) != null;
                        } else {
                            System.out.println("Something very strange is happening in Content");
                        }

                        if (labelInTheRightStory && labelCell.getStringCellValue().equals(label)) {
                            System.out.println("test: " + test + " label: " + label + " story " + finalStoryValue);
                            setStatusColor(Cursor.getTestRow(), labelCell.getColumnIndex(), status);
                        }
                    });
                }
            }
            else setDeviation(Cursor.getTestRow(), status);
            Cursor.moveBottom();
        });
    }

    private void setHeaderValue(String value, int row) {
        Cell cell = getCell(row, INITIAL_COLUMN_POSITION);
        cell.setCellValue(value);
        HyperLink.setURL(value, cell, book);
        cell.setCellStyle(docStyles.get(Style.TEST));
    }

    private void setDeviation(int row, TestStatus status) {
        Cell cell = getCell(row, INITIAL_COLUMN_POSITION);
        XSSFCellStyle cellStyle = book.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        cellStyle.setFillForegroundColor(color.getStatusColors().get(status));
        cell.setCellStyle(cellStyle);
    }

    static int countWTF = 1;

    private void setStatusColor(int row, int column, TestStatus status) {
        System.out.println("row: " + row + " column: " + column + " status: " + status + " iter " + countWTF);
        countWTF++;
        Cell coloredCell = getCell(row, column);
        coloredCell.setCellStyle(statusStyles.get(status));
    }

}
