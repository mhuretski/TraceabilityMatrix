package tracibility.excel.export;

import org.apache.poi.ss.usermodel.Workbook;
import tracibility.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static tracibility.properties.Constants.EXCEL_FILE_NAME;
import static tracibility.properties.Constants.PASS_TO_EXPORT_RESULT;

public class Writer {

    public void write(Workbook book) {
        if (book != null) {
            new File(PASS_TO_EXPORT_RESULT).mkdirs();
            try (FileOutputStream fos = new FileOutputStream(PASS_TO_EXPORT_RESULT + EXCEL_FILE_NAME)) {
                book.write(fos);
                Logger.log("Book is written to file");
            } catch (IOException e) {
                e.printStackTrace();
                Logger.logErrorMessage(e);
            } finally {
                try {
                    book.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.logErrorMessage(e);
                }
            }
        } else System.out.println("Book is not created");
    }

}
