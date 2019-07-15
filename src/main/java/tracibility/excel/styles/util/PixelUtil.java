package tracibility.excel.styles.util;

public class PixelUtil {

    private static final short EXCEL_COLUMN_WIDTH_FACTOR = 256;
    private static final float EXCEL_ROW_HEIGHT_FACTOR = 0.75f;
    private static final int UNIT_OFFSET_LENGTH = 7;
    private static final int[] UNIT_OFFSET_MAP = new int[]{0, 36, 73, 109, 146, 182, 219};

    public static short pixel2WidthUnits(int pxs) {
        short widthUnits = (short) (EXCEL_COLUMN_WIDTH_FACTOR * (pxs / UNIT_OFFSET_LENGTH));
        widthUnits += UNIT_OFFSET_MAP[(pxs % UNIT_OFFSET_LENGTH)];
        return widthUnits;
    }

    public static float pixel2HeightPoints(int pxs) {
        return pxs * EXCEL_ROW_HEIGHT_FACTOR;
    }

}
