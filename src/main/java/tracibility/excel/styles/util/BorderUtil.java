package tracibility.excel.styles.util;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static tracibility.properties.Constants.SHEET_NAME;

public class BorderUtil {

    public static void setBorder(XSSFWorkbook book, CellRangeAddress region) {
        setBorderTop(book, region);
        setBorderBottom(book, region);
        setBorderLeft(book, region);
        setBorderRight(book, region);
    }

    public static void setBorderTop(XSSFWorkbook book, CellRangeAddress region) {
        Row row = book.getSheet(SHEET_NAME).getRow(region.getFirstRow());
        for (int i = region.getFirstColumn(); i <= region.getLastColumn(); i++) {
            Cell cell = row.getCell(i);
            CellStyle cellStyle = book.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            cellStyle.setBorderTop(BorderStyle.MEDIUM);
            cell.setCellStyle(cellStyle);
        }
    }

    public static void setBorderBottom(XSSFWorkbook book, CellRangeAddress region) {
        Row row = book.getSheet(SHEET_NAME).getRow(region.getLastRow());
        for (int i = region.getFirstColumn(); i <= region.getLastColumn(); i++) {
            Cell cell = row.getCell(i);
            CellStyle cellStyle = book.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            cellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cell.setCellStyle(cellStyle);
        }
    }

    public static void setBorderLeft(XSSFWorkbook book, CellRangeAddress region) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Cell cell = book.getSheet(SHEET_NAME).getRow(i).getCell(region.getFirstColumn());
            CellStyle cellStyle = book.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            cellStyle.setBorderLeft(BorderStyle.MEDIUM);
            cell.setCellStyle(cellStyle);
        }
    }

    public static void setBorderLeftForCell(Cell cell, XSSFWorkbook book) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.cloneStyleFrom(cell.getCellStyle());
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cell.setCellStyle(cellStyle);
    }

    public static void setBorderRight(XSSFWorkbook book, CellRangeAddress region) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Cell cell = book.getSheet(SHEET_NAME).getRow(i).getCell(region.getLastColumn());
            CellStyle cellStyle = book.createCellStyle();
            cellStyle.cloneStyleFrom(cell.getCellStyle());
            cellStyle.setBorderRight(BorderStyle.MEDIUM);
            cell.setCellStyle(cellStyle);
        }
    }

}