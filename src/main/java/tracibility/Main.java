package tracibility;

import tracibility.excel.ExcelExecutor;
import tracibility.excel.export.Writer;
import tracibility.jira.JiraExecutor;
import tracibility.logger.Logger;
import tracibility.properties.TestStatus;
import tracibility.util.StructureAnalyzer;
import tracibility.zephyr.ZephyrExecutor;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            Map<String, TestStatus> horizontalStructure = new ZephyrExecutor().getHorizontalStructure();
            Map<String, Map<String, Map<String, Map<String, List<String>>>>> verticalStructure =
                    new JiraExecutor().getVerticalStructure();
            StructureAnalyzer.getReworkedVert(verticalStructure);
            StructureAnalyzer.calculatePercentage(horizontalStructure, verticalStructure);
            ExcelExecutor ee = new ExcelExecutor();
            ee.generate(horizontalStructure, verticalStructure);
            new Writer().write(ee.getBook());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
    }

}
