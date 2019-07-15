package tracibility.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tracibility.excel.factory.Headers;
import tracibility.excel.factory.Matrix;
import tracibility.excel.factory.Preparation;
import tracibility.excel.factory.content.structure.Body;
import tracibility.excel.factory.content.structure.Content;
import tracibility.excel.factory.content.structure.TopHeader;
import tracibility.excel.factory.content.util.Beautifier;
import tracibility.logger.Logger;
import tracibility.properties.TestStatus;

import java.util.List;
import java.util.Map;

public class ExcelExecutor {

    private XSSFWorkbook book;
    private XSSFSheet sheet;

    public void generate(Map<String, TestStatus> horizontalStructure,
                         Map<String, Map<String, Map<String, Map<String, List<String>>>>> verticalStructure) {
        Logger.log("Creating excel file...");
        Preparation prep = new Preparation();
        prepareBook(prep);
        Matrix.create(horizontalStructure, verticalStructure, sheet);
        Headers headers = new Headers(prep);
        headers.create();
        TopHeader topHeaderContent = new TopHeader(prep);
        topHeaderContent.create();
        Body body = new Body(prep);
        body.create();
        Content content = new Content(prep);
        content.createAndSetDependencies();
        Beautifier.setBorders(book);
        Logger.log("Excel is generated");
    }

    private void prepareBook(Preparation prep) {
        book = prep.getBook();
        sheet = prep.getSheet();
    }

    public XSSFWorkbook getBook() {
        return book;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

}
