package tracibility.zephyr.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tracibility.zephyr.util.Driver;

public class ZephyrApp {

    private Driver driver;

    public ZephyrApp(Driver driver) {
        this.driver = driver;
    }

    public void switchToZephyr() {
        WebElement zephyr = driver.findElement(By.cssSelector("iframe[id*='zephyr']"));
        driver.switchTo().frame(zephyr);
    }

    public void waitLoader() {
        boolean loaderIsPresent = checkLoaderPresence();
        while (loaderIsPresent) loaderIsPresent = checkLoaderPresence();
    }

    private boolean checkLoaderPresence() {
        return driver.findElements(By.cssSelector("div.loader-bar")).size() > 0;
    }

    public void switchFromZephyr() {
        driver.switchTo().defaultContent();
    }

}
