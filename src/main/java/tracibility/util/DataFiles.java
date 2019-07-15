package tracibility.util;

import java.io.File;

import static tracibility.properties.Constants.PASS_TO_TEMP_FILES;

public class DataFiles {

    public static void createTempDirectory() {
        File file = new File(PASS_TO_TEMP_FILES);
        file.mkdirs();
    }

}
