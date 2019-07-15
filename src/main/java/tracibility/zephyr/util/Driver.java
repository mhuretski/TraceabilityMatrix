package tracibility.zephyr.util;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static tracibility.properties.Constants.PASS_TO_CHROME_DRIVER;

public class Driver extends ChromeDriver {

    public Driver(ChromeOptions options) {
        super(ChromeDriverService.createDefaultService(), options);
    }

    static {
        System.setProperty("webdriver.chrome.driver", PASS_TO_CHROME_DRIVER);
    }

    public void init() {
        this.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        this.manage().window().maximize();
    }

}
