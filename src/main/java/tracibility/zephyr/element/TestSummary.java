package tracibility.zephyr.element;

import org.openqa.selenium.By;
import tracibility.zephyr.util.Driver;

public class TestSummary {

    private Driver driver;
    private ZephyrApp za;

    public TestSummary(ZephyrApp za, Driver driver) {
        this.driver = driver;
        this.za = za;
    }

    public void openCycleSummary() {
        za.switchToZephyr();
        za.waitLoader();
        driver.findElement(By.cssSelector("a[href*='test-cycles-tab']"))
                .click();
    }

}
