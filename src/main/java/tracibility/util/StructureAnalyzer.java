package tracibility.util;

import tracibility.logger.Logger;
import tracibility.properties.TestStatus;

import java.util.List;
import java.util.Map;

public class StructureAnalyzer {

    private static double testCoveragePercent;
    private static double successfulPassPercent;

    public static void getReworkedVert(Map<String, Map<String, Map<String, Map<String, List<String>>>>> map) {
        map.forEach((component, epicVal) ->
                epicVal.forEach((epic, storyVal) ->
                        storyVal.entrySet().removeIf(story ->
                                story.getValue() == null || story.getValue().isEmpty())));
        map.forEach((component, epicVal) ->
                epicVal.entrySet().removeIf(epic ->
                        epic.getValue() == null || epic.getValue().isEmpty()));
        map.entrySet().removeIf(component ->
                component.getValue() == null || component.getValue().isEmpty());
    }

    public static void calculatePercentage(Map<String, TestStatus> horMap,
                                           Map<String, Map<String, Map<String, Map<String, List<String>>>>> vertMap) {
        int[] amountOfLabels = new int[]{0};
        int[] amountOfEmptyLabels = new int[]{0};
        vertMap.forEach((component, epicVal) ->
                epicVal.forEach((epic, storyVal) ->
                        storyVal.forEach((story, labelVal) ->
                                labelVal.forEach((label, testVal) -> {
                                    amountOfLabels[0]++;
                                    if (testVal.isEmpty())
                                        amountOfEmptyLabels[0]++;
                                }))));
        testCoveragePercent = (double) (amountOfLabels[0] - amountOfEmptyLabels[0]) / amountOfLabels[0];
        int amountOfTests = horMap.size();
        int amountOfPassTests = horMap.values()
                .stream()
                .mapToInt(value ->
                        (value.equals(TestStatus.PASS)) ? 1 : 0)
                .sum();
        successfulPassPercent = (double) amountOfPassTests / amountOfTests;
        Logger.log("Percentage is calculated");
    }

    public static double getTestCoveragePercent() {
        return testCoveragePercent;
    }

    public static double getSuccessfulPassPercent() {
        return successfulPassPercent;
    }

    public static double getTotalProgressPercent() {
        return testCoveragePercent * successfulPassPercent;
    }

}
