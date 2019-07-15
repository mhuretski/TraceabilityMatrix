package tracibility.zephyr.element;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tracibility.zephyr.util.Driver;

public class NavigationMenu {

    private Driver driver;

    public NavigationMenu(Driver driver) {
        this.driver = driver;
    }

    public void clickTestsBtn() {
        By testsXpath = By.xpath("//div[contains(text(),'Tests')]");
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(testsXpath));
        driver.findElement(testsXpath).click();
    }

    public void clickTestSummaryBtn() {
        driver.findElement(By.xpath("//a//div[contains(text(),'Test Summary')]"))
                .click();
    }

}
