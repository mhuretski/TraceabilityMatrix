package tracibility.zephyr.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tracibility.zephyr.util.Driver;

import java.util.List;

public class ExportPopup {

    private Driver driver;
    private ZephyrApp za;

    public ExportPopup(ZephyrApp za, Driver driver) {
        this.driver = driver;
        this.za = za;
    }

    public void exportXML() {
        chooseXML();
        executeExport();
        waitExportToFinish();
        closeExportPopup();
    }

    private void chooseXML() {
        WebElement dropdownMenu = driver.findElement(By.cssSelector("div.ak-field-flex-grow"));
        dropdownMenu.click();
        WebElement elementXML = driver.findElement(By.xpath("//span[contains(text(),'XML')]"));
        elementXML.click();
    }

    private void executeExport() {
        driver.findElement(By.cssSelector("button[class*='ak-button__appearance-primary']"))
                .click();
    }

    private void waitExportToFinish() {
        List<WebElement> progressBar = getDownloadStatus();
        while (progressBar.size() == 0) progressBar = getDownloadStatus();
    }

    private List<WebElement> getDownloadStatus() {
        return driver.findElements(By.cssSelector("div[class='progress-bar'] span[style*='100']"));
    }

    private void closeExportPopup() {
        driver.findElement(By.cssSelector("div[class='modal-footer'] span"))
                .click();
    }

}
