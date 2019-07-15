package tracibility.excel.factory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import tracibility.properties.TestStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tracibility.properties.Constants.DEFAULT_LABEL;

public class Matrix {

    public static final int MIN_AMOUNT_OF_COLUMNS = 15;
    private static Object[][][] matrix;
    private static int numberOfRows;
    private static int numberOfColumns;
    private static Map<String, Map<String, Map<String, Map<String, List<String>>>>> verticalStructure;
    private static Map<String, List<String>> plainVerticalStructure;
    private static Map<String, TestStatus> horizontalStructure;
    private static int[] amountOfOtherTag = new int[1];

    public static void create(Map<String, TestStatus> horizontalStructure,
                              Map<String, Map<String, Map<String, Map<String, List<String>>>>> verticalStructure,
                              Sheet sheet) {
        Matrix.horizontalStructure = horizontalStructure;
        Matrix.verticalStructure = verticalStructure;
        plainVerticalStructure = new HashMap<>();
        numberOfRows = horizontalStructure.size() + 21;
        //component -> epic -> story -> label -> test
        verticalStructure.forEach((component, epicVal) ->
                epicVal.forEach((epic, storyVal) ->
                        storyVal.forEach((story, labelVal) ->
                                labelVal.forEach((label, test) -> {
                                    if (label.equals(DEFAULT_LABEL))
                                        amountOfOtherTag[0]++;
                                    if (!plainVerticalStructure.containsKey(label)) {
                                        plainVerticalStructure.put(label, new ArrayList<>());
                                    }
                                    plainVerticalStructure.get(label).addAll(test);
                                }))));
        int defaultAmountOfColumns = (plainVerticalStructure.size()
                + amountOfOtherTag[0]
                + ((amountOfOtherTag[0] == 0) ? 1 : 0)
                + 2);
        numberOfColumns = (defaultAmountOfColumns < MIN_AMOUNT_OF_COLUMNS)
                ? MIN_AMOUNT_OF_COLUMNS : defaultAmountOfColumns;
        create(sheet);
    }

    private static void create(Sheet sheet) {
        matrix = new Object[numberOfRows][][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new Object[2][];
            matrix[i][0] = new Object[1]; //row object
            matrix[i][1] = new Object[numberOfColumns]; //array of cells in row
            Row row = sheet.createRow(i);
            matrix[i][0][0] = row;
            for (int j = 0; j < matrix[i][1].length; j++) {
                Cell cell = row.createCell(j);
                matrix[i][1][j] = cell;
            }
        }
    }

    public static Row getRow(int rowIndex) {
        return ((Row) matrix[rowIndex][0][0]);
    }

    public static Cell getCell(int row, int column) {
        return ((Cell) matrix[row][1][column]);
    }

    public static Map<String, Map<String, Map<String, Map<String, List<String>>>>> getVerticalStructure() {
        return verticalStructure;
    }

    public static Map<String, List<String>> getPlainVerticalStructure() {
        return plainVerticalStructure;
    }

    public static Map<String, TestStatus> getHorizontalStructure() {
        return horizontalStructure;
    }

    public static int getEndColumnWithContent() {
        return plainVerticalStructure.size()
                + amountOfOtherTag[0]
                + ((amountOfOtherTag[0] == 0) ? 1 : 0);
    }

    public static int getNumberOfRows() {
        return numberOfRows;
    }

    public static int getNumberOfColumns() {
        return numberOfColumns;
    }

}
