package tracibility.zephyr.element;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import tracibility.zephyr.util.Driver;

import java.util.List;

import static tracibility.properties.Constants.EMPTY_STRING;
import static tracibility.properties.Constants.SPRINT;

public class CycleSummary {

    private Driver driver;
    private ZephyrApp za;

    public CycleSummary(ZephyrApp za, Driver driver) {
        this.driver = driver;
        this.za = za;
    }

    public int exportCycles() {
        za.switchToZephyr();
        za.waitLoader();
        if (SPRINT.equals(EMPTY_STRING)) return exportAllCycles();
        else return exportCycle();
    }

    private int exportAllCycles() {
        List<WebElement> cycles = getCycles();
        while (cycles.size() == 0) cycles = getCycles();
        ExportPopup ep = new ExportPopup(za, driver);
        cycles.forEach(cycle -> {
            za.waitLoader();
            openCycleMenu(cycle);
            openExportCyclePopup();
            ep.exportXML();
        });
        return cycles.size();
    }

    private int exportCycle() {
        List<WebElement> cycle = getSpecificCycle();
        while (cycle.size() != 1) cycle = getSpecificCycle();
        ExportPopup ep = new ExportPopup(za, driver);
        za.waitLoader();
        openCycleMenu(cycle.get(0));
        openExportCyclePopup();
        ep.exportXML();
        return cycle.size();
    }

    private List<WebElement> getSpecificCycle() {
        return driver.findElements(By.xpath("//span[@title='" + SPRINT + "']/ancestor::div[contains(@class,'node-type-cycle')]/div[4]"));
    }

    private List<WebElement> getCycles() {
        return driver.findElements(By.cssSelector("div[class='context-wrapper']"));
    }

    private void openCycleMenu(WebElement cycle) {
        cycle.click();
    }

    private void openExportCyclePopup() {
        driver.findElement(By.xpath("//span[contains(text(),'Export Cycle')]"))
                .click();
    }

}
