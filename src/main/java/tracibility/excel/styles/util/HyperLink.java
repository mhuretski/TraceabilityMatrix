package tracibility.excel.styles.util;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static tracibility.properties.Constants.*;

public class HyperLink {

    private static XSSFCreationHelper createHelper = null;

    public static void setURL(String value, Cell cell, XSSFWorkbook book) {
        if (createHelper == null)
            createHelper = book.getCreationHelper();
        XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
        link.setAddress(LINK_TO_JIRA + value);
        cell.setHyperlink(link);
    }

    public static void setComponentURL(String value, Cell cell, XSSFWorkbook book) {
        if (createHelper == null)
            createHelper = book.getCreationHelper();
        XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.URL);
        link.setAddress(LINK_TO_JIRA_COMPONENT + value);
        cell.setHyperlink(link);
    }

}
