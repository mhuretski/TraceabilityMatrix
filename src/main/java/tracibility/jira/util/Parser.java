package tracibility.jira.util;

import org.json.JSONArray;
import org.json.JSONObject;
import tracibility.util.SprintFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static tracibility.properties.Constants.*;

public class Parser {

    public static Map<String, List<String>> STORIES_TO_TESTS = new HashMap<>();

    protected List<String> componentIds = new ArrayList<>();

    protected Map<String, List<String>> componentsToEpics = new HashMap<>();
    protected List<String> allEpics = new ArrayList<>();

    protected Map<String, List<String>> epicsToStories = new HashMap<>();
    protected List<String> allStories = new ArrayList<>();

    protected Map<String, List<String>> storiesToLabels = new HashMap<>();
    protected List<String> allTests = new ArrayList<>();
    protected List<String> allLabelsInStories = new ArrayList<>();

    protected List<String> allLabelsInTests = new ArrayList<>();
    protected Map<String, List<String>> testsToLabels = new HashMap<>();
    protected Map<String, List<String>> labelsToTests = new HashMap<>();

    protected void getComponents(Supplier<String> getComponentsJsonResponse) {
        String componentsJsonResponse = getComponentsJsonResponse.get();
        JSONObject obj = new JSONObject(componentsJsonResponse);
        JSONArray arr = obj.getJSONArray("components");
        for (int i = 0; i < arr.length(); i++) {
            String componentId = arr.getJSONObject(i).getString("id");
            componentIds.add(componentId);
        }
    }

    protected void getComponentsToEpics(Function<String, String> getAllEpicsInComponentJsonResponse) {
        componentIds.forEach(componentId -> {
            componentsToEpics.put(componentId, new ArrayList<>());
            String allEpicsInComponent = getAllEpicsInComponentJsonResponse.apply(componentId);
            JSONObject obj1 = new JSONObject(allEpicsInComponent);
            JSONArray arr1 = obj1.getJSONArray("issues");
            for (int i = 0; i < arr1.length(); i++) {
                JSONObject entity = arr1.getJSONObject(i);
                JSONObject issuetypes = entity.getJSONObject("fields").getJSONObject("issuetype");
                String issueType = issuetypes.getString("name");
                if (issueType.equals("Epic")) {
                    String epicKey = entity.getString("key");
                    List<String> epics = componentsToEpics.get(componentId);
                    epics.add(epicKey);
                    allEpics.add(epicKey);
                }
            }
        });
    }

    protected void getEpicsToStories(Function<String, String> getAllEpicsInComponentJsonResponse) {
        allEpics.forEach(epicId -> {
            epicsToStories.put(epicId, new ArrayList<>());
            String allStoriesInEpic = getAllEpicsInComponentJsonResponse.apply(epicId);
            JSONObject obj1 = new JSONObject(allStoriesInEpic);
            JSONArray arr1 = obj1.getJSONArray("issues");
            for (int i = 0; i < arr1.length(); i++) {
                JSONObject entity = arr1.getJSONObject(i);
                JSONObject issuetypes = entity.getJSONObject("fields").getJSONObject("issuetype");
                String issueType = issuetypes.getString("name");
                if (issueType.equals("Story")) {
                    String storyKey = entity.getString("key");
                    List<String> stories = epicsToStories.get(epicId);
                    stories.add(storyKey);
                    allStories.add(storyKey);
                }
            }
        });
    }



    protected void getStoriesToTestsAndLabels(Function<String, String> getAllTestsAndLabelsInStoryJsonResponse) {
        allStories.forEach(storyId -> {
            STORIES_TO_TESTS.put(storyId, new ArrayList<>());
            storiesToLabels.put(storyId, new ArrayList<>());
            String allTestsAndLabelsInStory = getAllTestsAndLabelsInStoryJsonResponse.apply(storyId);
            JSONObject fields = new JSONObject(allTestsAndLabelsInStory).getJSONObject("fields");
            if (SPRINT.equals(EMPTY_STRING) || SprintFilter.isPresent(fields)) {
                JSONArray labels = fields.getJSONArray("labels");
                labels.forEach(label -> {
                    String labelStr = label.toString();
                    if (filterLabels(labelStr)) {
                        allLabelsInStories.add(labelStr);
                        List<String> labelsInStory = storiesToLabels.get(storyId);
                        labelsInStory.add(labelStr);
                    }
                });
                JSONArray linkedTests = fields.getJSONArray("issuelinks");
                for (int i = 0; i < linkedTests.length(); i++) {
                    JSONObject issuelinks = linkedTests.getJSONObject(i);
                    JSONObject issueType = (issuelinks.has("outwardIssue"))
                            ? issuelinks.getJSONObject("outwardIssue")
                            : issuelinks.getJSONObject("inwardIssue");
                    String name = issueType.getJSONObject("fields").getJSONObject("issuetype").getString("name");
                    if (name.equals("Test")) {
                        String testId = issueType.getString("key");
                        List<String> tests = STORIES_TO_TESTS.get(storyId);
                        tests.add(testId);
                        allTests.add(testId);
                    }
                }
            }
        });
    }

    protected void getAllLabelsInTests(Function<String, String> getAllLabelsInTestJsonResponse) {
        allTests.forEach(testId -> {
            testsToLabels.put(testId, new ArrayList<>());
            String allLabelsInTest = getAllLabelsInTestJsonResponse.apply(testId);
            JSONObject fields = new JSONObject(allLabelsInTest).getJSONObject("fields");
            JSONArray labels = fields.getJSONArray("labels");
            labels.forEach(label -> {
                String labelStr = label.toString();
                if (filterLabels(labelStr)) {
                    List<String> labelsInTests = testsToLabels.get(testId);
                    labelsInTests.add(labelStr);
                    allLabelsInTests.add(labelStr);
                }
            });
        });
    }

    private boolean filterLabels(String label) {
        return FILTER == null || FILTER.equals(EMPTY_STRING) || label.startsWith(FILTER);
    }

    protected void reverseMapLabelsInTests() {
        testsToLabels.forEach((test, labels) -> {
            if (labels.isEmpty())
                putToDefaultLabels(test);
            else
                labels.forEach(label -> {
                    if (allLabelsInStories.contains(label)) {
                        if (!labelsToTests.containsKey(label)) {
                            labelsToTests.put(label, new ArrayList<>());
                        }
                        labelsToTests.get(label).add(test);
                    } else putToDefaultLabels(test);
                });
        });
    }

    private void putToDefaultLabels(String test) {
        if (!labelsToTests.containsKey(DEFAULT_LABEL)) {
            labelsToTests.put(DEFAULT_LABEL, new ArrayList<>());
        }
        labelsToTests.get(DEFAULT_LABEL).add(test);
    }

}
