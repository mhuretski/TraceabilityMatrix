package tracibility.jira;

import tracibility.jira.util.Parser;
import tracibility.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static tracibility.jira.util.Request.get;
import static tracibility.properties.Constants.*;


public class JiraExecutor extends Parser {

    //component -> epic -> story -> label -> test
    private Map<String, Map<String, Map<String, Map<String, List<String>>>>> verticalStructure = new HashMap<>();

    public Map<String, Map<String, Map<String, Map<String, List<String>>>>> getVerticalStructure() {
        Logger.log("Creating Vertical structure...");
        prepareStructure();
        setDependencies();
        Logger.log("Vertical structure created");
        return verticalStructure;
    }

    private void setDependencies() {
        Logger.log("Setting dependencies...");
        componentIds.forEach(component -> {
            verticalStructure.put(component, new HashMap<>());
            Map<String, Map<String, Map<String, List<String>>>> allEpics = verticalStructure.get(component);
            componentsToEpics.get(component).forEach(epic -> {
                allEpics.put(epic, new HashMap<>());
                Map<String, Map<String, List<String>>> allStories = allEpics.get(epic);
                epicsToStories.get(epic).forEach(story -> {
                    allStories.put(story, new HashMap<>());
                    Map<String, List<String>> allLabels = allStories.get(story);
                    storiesToLabels.get(story).forEach(label -> { //all labels in story
                        allLabels.put(label, new ArrayList<>());
                        List<String> allTests = allLabels.get(label);
                        List<String> testsInLabel = labelsToTests.get(label); //all tests in label
                        if (testsInLabel != null) allTests.addAll(testsInLabel);
                    });
                    if (labelsToTests.get(DEFAULT_LABEL) != null) {
                        List<String> allLabeledTestsWithoutSameLabelsInStories = labelsToTests.get(DEFAULT_LABEL);
                        STORIES_TO_TESTS.get(story).forEach(test -> { //all tests in story
                            if (allLabeledTestsWithoutSameLabelsInStories.contains(test)) {
                                allLabels.computeIfAbsent(DEFAULT_LABEL, label -> new ArrayList<>());
                                allLabels.get(DEFAULT_LABEL).add(test);
                            }
                        });
                    }
                });
            });
        });
        System.out.println("Vertical structure " + verticalStructure);
    }

    private void prepareStructure() {
        Logger.log("Preparing structure...");
        String pathToAllComponents = "/rest/api/2/project/" + PROJECT;
        Supplier<String> getComponentsJsonResponse =
                () -> get(DOMAIN + pathToAllComponents);
        getComponents(getComponentsJsonResponse);
        System.out.println("All Components " + componentIds);

        String pathToEpicFromComponent = "/rest/api/2/search?jql=component%20%3D%20";
        Function<String, String> getAllEpicsInComponentJsonResponse =
                componentId -> get(DOMAIN + pathToEpicFromComponent + componentId);
        getComponentsToEpics(getAllEpicsInComponentJsonResponse);
        System.out.println("Components to Epics " + componentsToEpics);
        System.out.println("All Epics " + allEpics);

        String storiesFromEpicPath = "/rest/agile/1.0/epic/";
        Function<String, String> getAllStoriesInEpicJsonResponse =
                epicId -> get(DOMAIN + storiesFromEpicPath + epicId + "/issue");
        getEpicsToStories(getAllStoriesInEpicJsonResponse);
        System.out.println("Epics to Stories " + epicsToStories);
        System.out.println("All Stories " + allStories);

        String testsAndLabelsFromStoryPath = "/rest/agile/1.0/issue/";
        Function<String, String> getAllTestsAndLabelsInStoryJsonResponse =
                storyId -> get(DOMAIN + testsAndLabelsFromStoryPath + storyId);
        getStoriesToTestsAndLabels(getAllTestsAndLabelsInStoryJsonResponse);
        System.out.println("Stories to Tests " + STORIES_TO_TESTS); //all tests in stories
        System.out.println("All Tests " + allTests); //list of all tests in stories
        System.out.println("Stories to Labels " + storiesToLabels); //all labels in stories
        System.out.println("All Labels in Stories " + allLabelsInStories); //list of all labels in stories

        String labelsInTestPath = "/rest/agile/1.0/issue/";
        Function<String, String> getAllLabelsInTestJsonResponse =
                testId -> get(DOMAIN + labelsInTestPath + testId);
        getAllLabelsInTests(getAllLabelsInTestJsonResponse);
        System.out.println("Tests to Labels " + testsToLabels); //all labels in tests in stories
        System.out.println("All Labels in Tests " + allLabelsInTests);

        reverseMapLabelsInTests();
        System.out.println("Labels to Tests " + labelsToTests); //reversed testsToLabels

    }

}
