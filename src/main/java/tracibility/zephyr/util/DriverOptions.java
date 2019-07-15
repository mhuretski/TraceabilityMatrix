package tracibility.zephyr.util;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

import static tracibility.properties.Constants.IS_HEADLESS;
import static tracibility.properties.Constants.PASS_TO_TEMP_FILES;

public class DriverOptions {
    public ChromeOptions get() {
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", PASS_TO_TEMP_FILES);
        chromePrefs.put("safebrowsing.enabled", false);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("test-type");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        if (IS_HEADLESS) options.addArguments("headless");
        return options;
    }
}
