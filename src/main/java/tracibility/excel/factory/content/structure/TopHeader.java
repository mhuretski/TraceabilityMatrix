package tracibility.excel.factory.content.structure;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.excel.factory.Matrix;
import tracibility.excel.factory.Preparation;
import tracibility.excel.factory.content.util.Cursor;
import tracibility.excel.styles.Style;
import tracibility.excel.styles.util.HyperLink;

import java.util.List;
import java.util.Map;

import static tracibility.excel.factory.Matrix.getCell;
import static tracibility.excel.factory.content.util.Cursor.*;
import static tracibility.excel.styles.util.Merge.mergeCellsInRow;
import static tracibility.properties.Constants.PROJECT;

public class TopHeader {

    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private Map<Style, XSSFCellStyle> docStyles;

    public TopHeader(Preparation prep) {
        book = prep.getBook();
        sheet = prep.getSheet();
        docStyles = prep.getDocStyles();
    }

    public void create() {
        setProject();
        setComponents();
    }

    private void setProject() {
        contentHeader(PROJECT,
                INITIAL_ROW_POSITION,
                INITIAL_COLUMN_CONTENT_POSITION,
                Matrix.getEndColumnWithContent());
    }

    private void setComponents() {
        Matrix.getVerticalStructure().forEach((component, epicVal) -> {
            Cursor.saveComponentPosition();
            epicVal.forEach((epic, storyVal) -> {
                Cursor.saveEpicPosition();
                storyVal.forEach((story, labelVal) -> {
                    Cursor.saveStoryPosition();
                    labelVal.forEach((label, testVal) -> {
                        boolean coverageExists = testVal != null && !testVal.isEmpty();
                        contentLabel(label, Cursor.getLabelColumn(), coverageExists);
                        setAmountOfTests(Cursor.getLabelColumn(), testVal, coverageExists);
                        Cursor.moveRight();
                    });
                    contentHeader(story, STORY_ROW, Cursor.getStartStoryColumn(), Cursor.getEndColumn());
                });
                contentHeader(epic, EPIC_ROW, Cursor.getStartEpicColumn(), Cursor.getEndColumn());
            });
            contentComponentHeader(component, Cursor.getStartComponentColumn(), Cursor.getEndColumn());
        });
        Cursor.saveStoryPosition();
    }

    private void contentHeader(String value, int row, int startCell, int endCell) {
        mergeCellsInRow(sheet, row, startCell, endCell);
        Cell cell = getCell(row, startCell);
        cell.setCellValue(value);
        HyperLink.setURL(value, cell, book);
        for (int i = startCell; i <= endCell; i++) {
            getCell(row, i).setCellStyle(docStyles.get(Style.HEADER_HYPERLINK));
        }
    }

    private void contentComponentHeader(String value, int startCell, int endCell) {
        mergeCellsInRow(sheet, COMPONENT_ROW, startCell, endCell);
        Cell cell = getCell(COMPONENT_ROW, startCell);
        cell.setCellValue(value);
        HyperLink.setComponentURL(value, cell, book);
        for (int i = startCell; i <= endCell; i++) {
            getCell(COMPONENT_ROW, i).setCellStyle(docStyles.get(Style.HEADER_HYPERLINK));
        }
    }

    private void contentLabel(String value, int column, boolean coverageExists) {
        Cell labelCell = getCell(LABEL_ROW, column);
        labelCell.setCellValue(value);
        if (coverageExists) labelCell.setCellStyle(docStyles.get(Style.LABEL));
        else labelCell.setCellStyle(docStyles.get(Style.EMPTY_LABEL));
    }

    private void setAmountOfTests(int column, List<String> testVal, boolean coverageExists) {
        Cell countCell = getCell(TEST_ROW, column);
        if (coverageExists) {
            countCell.setCellValue(testVal.size());
            countCell.setCellStyle(docStyles.get(Style.COUNT));
        } else {
            countCell.setCellValue(0);
            countCell.setCellStyle(docStyles.get(Style.EMPTY_COUNT));
        }
    }

}
