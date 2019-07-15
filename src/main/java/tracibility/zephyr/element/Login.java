package tracibility.zephyr.element;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import tracibility.zephyr.util.Driver;

import static tracibility.properties.Constants.*;

public class Login {

    private Driver driver;

    public Login(Driver driver) {
        this.driver = driver;
    }

    public void execute() {
        driver.get(DOMAIN);
        WebElement loginField = driver.findElement(By.id("username"));
        loginField.sendKeys(USERNAME);
        loginField.sendKeys(Keys.ENTER);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(PASSWORD);
        passwordField.sendKeys(Keys.ENTER);
    }

}
