package tracibility.properties;

import tracibility.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static tracibility.properties.Constants.PATH_TO_PROPERTIES;

public class Properties {

    private static java.util.Properties properties;

    private static void init() {
        try (InputStream input = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties = new java.util.Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
    }

    public static java.util.Properties get() {
        if (properties == null) init();
        return properties;
    }

}
