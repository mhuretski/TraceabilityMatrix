package tracibility.zephyr.file;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.xml.sax.SAXException;
import tracibility.logger.Logger;
import tracibility.properties.TestStatus;
import tracibility.zephyr.file.entity.ExecutionStatus;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static tracibility.properties.Constants.DATE_PATTERN;
import static tracibility.properties.Constants.PASS_TO_TEMP_FILES;

public class ZephyrFilesImport {

    public List<String> getFilesPaths() {
        List<String> filePaths = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(PASS_TO_TEMP_FILES))) {
            filePaths = paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(item -> !item.endsWith("tmp"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
        return filePaths;
    }

    public void waitAllFilesToHaveSize() {
        List<String> pathsToImport = getFilesPaths();
        pathsToImport.forEach(this::waitForFileToHaveSize);
    }

    private void waitForFileToHaveSize(String file) {
        File sourceFile = new File(file);
        while (sourceFile.length() == 0) {
            System.out.println("123 " + sourceFile.length());
            sourceFile = new File(file);
        }
    }

    /*Zephyr exports with empty line before XML content*/
    public void prepareXMLFile(String xmlFile) {
        File sourceFile = new File(xmlFile);
        while (sourceFile.length() == 0) sourceFile = new File(xmlFile);
        File targetFile = new File(xmlFile + ".target");

        System.out.println("validating xml");
        while (invalidXml(sourceFile)) ;
        copyData(sourceFile, targetFile);

        while (!sourceFile.delete()) ;
        while (!targetFile.renameTo(sourceFile)) ;
    }

    private boolean invalidXml(File targetFile) {
        BufferedReader reader;
        FileReader fr;

        try {
            fr = new FileReader(targetFile);
            reader = new BufferedReader(fr);

            String line;
            String requiredLine = "";
            while ((line = reader.readLine()) != null) {
                requiredLine = line;
            }

            reader.close();
            fr.close();

            if (requiredLine.endsWith("executions>")) return false;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
        return true;
    }

    private void copyData(File sourceFile, File targetFile) {
        BufferedWriter writer;
        BufferedReader reader;
        OutputStreamWriter osw;
        FileOutputStream fos;
        FileReader fr;
        try {
            fos = new FileOutputStream(targetFile);
            osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new BufferedWriter(osw);

            fr = new FileReader(sourceFile);
            reader = new BufferedReader(fr);

            String line;
            if ((line = reader.readLine()) != null) {
                if (!(line.equals("\n") || line.equals(""))) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

            writer.close();
            osw.close();
            fos.close();

            reader.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
    }

    public Document parseFile(String xmlFile) {
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            org.w3c.dom.Document w3cDocument = documentBuilder.parse(xmlFile);
            document = new DOMBuilder().build(w3cDocument);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
        return document;
    }

    public Map<String, ExecutionStatus> getTestStatusFromFile(Document document) {
        Map<String, ExecutionStatus> testStatus = new HashMap<>();
        Element rootNode = document.getRootElement();
        rootNode.getChildren("execution").forEach(execution -> {
            String issueKey = execution.getChildText("issueKey");
            String executedStatus = execution.getChildText("executedStatus");
            String executedOn = execution.getChildText("executedOn");
            ZonedDateTime executedOnDate = null;
            if (!executedOn.equals(""))
                executedOnDate = ZonedDateTime.parse(executedOn,
                        DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.US));

            if (!testStatus.containsKey(issueKey)) {
                testStatus.put(issueKey, new ExecutionStatus(executedOnDate, executedStatus));
            } else {
                ExecutionStatus es = testStatus.get(issueKey);
                ZonedDateTime storedDate = es.getExecutedOnDate();
                if (storedDate == null) {
                    es.update(executedOnDate, executedStatus);
                } else if (executedOnDate != null && storedDate.isBefore(executedOnDate)) {
                    es.update(executedOnDate, executedStatus);
                }
            }
        });

        return testStatus;
    }

    public Map<String, TestStatus> getResultStatus(List<Map<String, ExecutionStatus>> testStatuses) {
        Map<String, ExecutionStatus> combinedTestStatuses = new HashMap<>();
        testStatuses.forEach(testStatusFromFile ->
                testStatusFromFile.forEach((key, value) ->
                        combinedTestStatuses.merge(key, value, (v1, v2) -> {
                            if (v1.getExecutedOnDate() == null) {
                                return v2;
                            } else if (v2.getExecutedOnDate() == null) {
                                return v1;
                            } else if (v1.getExecutedOnDate().isBefore(v2.getExecutedOnDate())) {
                                return v2;
                            } else return v1;
                        })));
        //test -> status
        return combinedTestStatuses.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        key -> {
                            switch (key.getValue().getExecutedStatus()) {
                                case "BLOCKED":
                                    return TestStatus.BLOCKED;
                                case "FAIL":
                                    return TestStatus.FAIL;
                                case "PASS":
                                    return TestStatus.PASS;
                                case "WIP":
                                    return TestStatus.WIP;
                                case "NOT APPLICABLE":
                                    return TestStatus.NOT_APPLICABLE;
                                case "UNEXECUTED":
                                    return TestStatus.UNEXECUTED;
                                default:
                                    return TestStatus.DEFAULT;
                            }
                        }));
    }

    public void deleteImportFiles(List<String> pathsToImport) {
        pathsToImport.stream()
                .map(File::new)
                .forEach(File::delete);
        new File(PASS_TO_TEMP_FILES).delete();
    }

}
