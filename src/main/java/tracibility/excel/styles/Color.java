package tracibility.excel.styles;

import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.properties.TestStatus;

import java.util.HashMap;
import java.util.Map;

public class Color {

    private final XSSFColor white;
    private final XSSFColor grey;
    private final XSSFColor lightGrey;
    private final XSSFColor blocked;
    private final XSSFColor fail;
    private final XSSFColor pass;
    private final XSSFColor wip;
    private final XSSFColor notApplicable;
    private final XSSFColor unexecuted;
    private final XSSFColor hyperLink;
    private final Map<TestStatus, XSSFColor> statusColors;

    public Color(XSSFWorkbook book) {
        IndexedColorMap colorMap = book.getStylesSource().getIndexedColors();
        white = new XSSFColor(new java.awt.Color(255, 255, 255), colorMap);
        grey = new XSSFColor(new java.awt.Color(217, 217, 217), colorMap);
        lightGrey = new XSSFColor(new java.awt.Color(242, 242, 242), colorMap);
        blocked = new XSSFColor(new java.awt.Color(255, 0, 102), colorMap);
        fail = new XSSFColor(new java.awt.Color(255, 0, 0), colorMap);
        pass = new XSSFColor(new java.awt.Color(0, 176, 80), colorMap);
        wip = new XSSFColor(new java.awt.Color(255, 217, 102), colorMap);
        notApplicable = new XSSFColor(new java.awt.Color(142, 169, 219), colorMap);
        unexecuted = new XSSFColor(new java.awt.Color(201, 201, 201), colorMap);
        hyperLink = new XSSFColor(new java.awt.Color(5, 99, 193), colorMap);
        statusColors = new HashMap<>();
        setMapping();
    }

    public XSSFColor getWhite() {
        return white;
    }

    public XSSFColor getGrey() {
        return grey;
    }

    public XSSFColor getLightGrey() {
        return lightGrey;
    }

    public XSSFColor getBlocked() {
        return blocked;
    }

    public XSSFColor getFail() {
        return fail;
    }

    public XSSFColor getPass() {
        return pass;
    }

    public XSSFColor getWip() {
        return wip;
    }

    public XSSFColor getNotApplicable() {
        return notApplicable;
    }

    public XSSFColor getUnexecuted() {
        return unexecuted;
    }

    public XSSFColor getHyperLink() {
        return hyperLink;
    }

    public Map<TestStatus, XSSFColor> getStatusColors() {
        return statusColors;
    }

    private void setMapping() {
        statusColors.put(TestStatus.BLOCKED, getBlocked());
        statusColors.put(TestStatus.FAIL, getFail());
        statusColors.put(TestStatus.PASS, getPass());
        statusColors.put(TestStatus.WIP, getWip());
        statusColors.put(TestStatus.NOT_APPLICABLE, getNotApplicable());
        statusColors.put(TestStatus.UNEXECUTED, getUnexecuted());
        statusColors.put(TestStatus.DEFAULT, getWhite());
    }

}
