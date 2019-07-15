package tracibility.excel.factory.content.util;

import tracibility.excel.factory.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Cursor {

    public static final int INITIAL_ROW_POSITION = 14;
    public static final int INITIAL_COLUMN_POSITION = 1;
    public static final int INITIAL_TEST_ROW_POSITION = INITIAL_ROW_POSITION + 6;
    public static final int INITIAL_COLUMN_CONTENT_POSITION = 2;
    public static final int COMPONENT_ROW = INITIAL_ROW_POSITION + 1;
    public static final int EPIC_ROW = INITIAL_ROW_POSITION + 2;
    public static final int STORY_ROW = INITIAL_ROW_POSITION + 3;
    public static final int LABEL_ROW = INITIAL_ROW_POSITION + 4;
    public static final int TEST_ROW = INITIAL_ROW_POSITION + 5;
    private static int column = INITIAL_COLUMN_CONTENT_POSITION;
    private static int row = INITIAL_TEST_ROW_POSITION;
    private static int storyPosition = column;
    private static int epicPosition = column;
    private static int componentPosition = column;
    private static final List<Integer> positions = new ArrayList<>();

    public static void moveRight() {
        column++;
    }

    public static void moveBottom() {
        row++;
    }

    public static int getTestRow() {
        return row;
    }

    public static int getLabelColumn() {
        return column;
    }

    public static int getEndColumn() {
        return column - 1;
    }

    public static int getStartComponentColumn() {
        return componentPosition;
    }

    public static int getStartEpicColumn() {
        return epicPosition;
    }

    public static int getStartStoryColumn() {
        return storyPosition;
    }

    public static void saveComponentPosition() {
        componentPosition = column;
    }

    public static void saveEpicPosition() {
        epicPosition = column;
    }

    public static void saveStoryPosition() {
        storyPosition = column;
        positions.add(storyPosition);
    }

    public static List<Integer> getLabelsPositions() {
        return positions;
    }

    public static int getLastColumn() {
        return positions.get(positions.size() - 1) - 1;
    }

    public static int getLastRow() {
        return INITIAL_TEST_ROW_POSITION + Matrix.getHorizontalStructure().size() - 1;
    }

}
