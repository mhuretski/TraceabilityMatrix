package tracibility.zephyr;

import org.jdom2.Document;
import tracibility.logger.Logger;
import tracibility.properties.TestStatus;
import tracibility.util.DataFiles;
import tracibility.zephyr.element.*;
import tracibility.zephyr.file.ZephyrFilesImport;
import tracibility.zephyr.file.entity.ExecutionStatus;
import tracibility.zephyr.util.Driver;
import tracibility.zephyr.util.DriverOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZephyrExecutor {

    public Map<String, TestStatus> getHorizontalStructure() {
        Logger.log("Creating Horizontal structure...");
        DataFiles.createTempDirectory();
        ZephyrFilesImport zfe = new ZephyrFilesImport();
        Driver driver = null;
        Map<String, TestStatus> horStr;
        try {
            driver = getFiles(zfe);
            horStr = getTestStatuses(zfe);
        } finally {
            if (driver != null) driver.close();
        }
        Logger.log("Horizontal structure created");
        System.out.println("Horizontal structure " + horStr);
        return horStr;
    }

    private Driver getFiles(ZephyrFilesImport zfe) {
        Logger.log("Getting files...");
        DriverOptions options = new DriverOptions();
        Driver driver = null;
        driver = new Driver(options.get());
        driver.init();
        new Login(driver).execute();
        NavigationMenu nm = new NavigationMenu(driver);
        nm.clickTestsBtn();
        nm.clickTestSummaryBtn();
        ZephyrApp za = new ZephyrApp(driver);
        new TestSummary(za, driver).openCycleSummary();
        waitTillFilesAreDownloaded(zfe, za, driver);
        Logger.log("Horizontal files are gotten");
        return driver;
    }

    private Map<String, TestStatus> getTestStatuses(ZephyrFilesImport zfe) {
        Logger.log("Getting test statuses...");
        List<String> pathsToImport = zfe.getFilesPaths();
        List<Map<String, ExecutionStatus>> testStatuses = new ArrayList<>();
        pathsToImport.forEach(pathToFile -> {
            zfe.prepareXMLFile(pathToFile);
            Document document = zfe.parseFile(pathToFile);
            Map<String, ExecutionStatus> testStatusFromFile = zfe.getTestStatusFromFile(document);
            testStatuses.add(testStatusFromFile);
        });
        zfe.deleteImportFiles(pathsToImport);
        return zfe.getResultStatus(testStatuses);
    }

    private void waitTillFilesAreDownloaded(ZephyrFilesImport zfe, ZephyrApp za, Driver driver) {
        int amountOfCycles = new CycleSummary(za, driver).exportCycles();
        int downloadedAmountOfFiles = zfe.getFilesPaths().size();
        while (amountOfCycles != downloadedAmountOfFiles)
            downloadedAmountOfFiles = zfe.getFilesPaths().size();
        Logger.log("Waiting files downloading...");
        boolean wait = true;
        while (wait) {
            String isDownloaded = zfe.getFilesPaths()
                    .stream()
                    .filter(item -> item.endsWith("tmp"))
                    .findAny()
                    .orElse(null);
            wait = isDownloaded != null;
        }
        zfe.waitAllFilesToHaveSize();
        Logger.log("Files downloaded");
    }

}
