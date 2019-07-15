import tracibility.excel.ExcelExecutor;
import tracibility.excel.export.Writer;
import tracibility.logger.Logger;
import tracibility.util.StructureAnalyzer;
import tracibility.properties.TestStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelStub {

    public static void main(String[] args) {
        ExcelExecutor ee = new ExcelExecutor();
        Map<String, TestStatus> hor = getStubHorMap();
        Map<String, Map<String, Map<String, Map<String, List<String>>>>> vert = getStubVertMap();
        StructureAnalyzer.getReworkedVert(vert);
        StructureAnalyzer.calculatePercentage(hor, vert);
        ee.generate(hor, vert);
        new Writer().write(ee.getBook());
    }

    public static void writeObjectToFile(Object serObj, String path) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(serObj);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
    }

    public static Map<String, Map<String, Map<String, Map<String, List<String>>>>> readVert(String path) {
        try (FileInputStream fi = new FileInputStream(new File(path));
             ObjectInputStream oi = new ObjectInputStream(fi)) {
            return (Map<String, Map<String, Map<String, Map<String, List<String>>>>>) oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
        return new HashMap<>();
    }

    public static Map<String, TestStatus> readHor(String path) {
        try (FileInputStream fi = new FileInputStream(new File(path));
             ObjectInputStream oi = new ObjectInputStream(fi)) {
            return (Map<String, TestStatus>) oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logErrorMessage(e);
        }
        return new HashMap<>();
    }

    public static Map<String, TestStatus> getStubHorMap() {
        Map<String, TestStatus> horizontalStructure = new HashMap<>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";
        String test5 = "test5";
        String test6 = "test6";
        String test7 = "test7";
        String test8 = "test8";
        String test9 = "test9";
        String test10 = "test10";
        String test11 = "test11";
        String test12 = "test12";
        String test13 = "test13";
        String test14 = "test14";
        String test15 = "test15";
        String test16 = "test16";
        horizontalStructure.put(test1, TestStatus.BLOCKED);
        horizontalStructure.put(test2, TestStatus.PASS);
        horizontalStructure.put(test3, TestStatus.WIP);
        horizontalStructure.put(test4, TestStatus.UNEXECUTED);
        horizontalStructure.put(test5, TestStatus.NOT_APPLICABLE);
        horizontalStructure.put(test6, TestStatus.FAIL);
        horizontalStructure.put(test7, TestStatus.BLOCKED);
        horizontalStructure.put(test8, TestStatus.PASS);
        horizontalStructure.put(test9, TestStatus.WIP);
        horizontalStructure.put(test10, TestStatus.UNEXECUTED);
        horizontalStructure.put(test11, TestStatus.NOT_APPLICABLE);
        horizontalStructure.put(test12, TestStatus.FAIL);
        horizontalStructure.put(test13, TestStatus.PASS);
        horizontalStructure.put(test14, TestStatus.PASS);
        horizontalStructure.put(test15, TestStatus.PASS);
        horizontalStructure.put(test16, TestStatus.PASS);
        return horizontalStructure;
    }

    public static Map<String, Map<String, Map<String, Map<String, List<String>>>>> getStubVertMap() {
        Map<String, Map<String, Map<String, Map<String, List<String>>>>> verticalStructure = new HashMap<>();
        String comp1 = "10002";
        String comp2 = "comp2";
        verticalStructure.put(comp1, new HashMap<>());
        verticalStructure.put(comp2, new HashMap<>());
        String epic1 = "epic1";
        String epic2 = "epic2";
        String epic3 = "epic3";
        String epic4 = "epic4";
        verticalStructure.get(comp1).put(epic1, new HashMap<>());
        verticalStructure.get(comp1).put(epic2, new HashMap<>());
        verticalStructure.get(comp2).put(epic3, new HashMap<>());
        verticalStructure.get(comp2).put(epic4, new HashMap<>());
        String story1 = "story1";
        String story2 = "story2";
        String story3 = "story3";
        String story4 = "story4";
        String story5 = "story5";
        String story6 = "story6";
        String story7 = "story7";
        String story8 = "story8";
        verticalStructure.get(comp1).get(epic1).put(story1, new HashMap<>());
        verticalStructure.get(comp1).get(epic1).put(story2, new HashMap<>());
        verticalStructure.get(comp1).get(epic2).put(story3, new HashMap<>());
        verticalStructure.get(comp1).get(epic2).put(story4, new HashMap<>());
        verticalStructure.get(comp2).get(epic3).put(story5, new HashMap<>());
        verticalStructure.get(comp2).get(epic3).put(story6, new HashMap<>());
        verticalStructure.get(comp2).get(epic4).put(story7, new HashMap<>());
        verticalStructure.get(comp2).get(epic4).put(story8, new HashMap<>());
        String label1 = "label1";
        String label2 = "label2";
        String label3 = "label3";
        String label4 = "label4";
        String label5 = "label5";
        String label6 = "label6";
        String label7 = "label7";
        String label8 = "label8";
        String label9 = "label9";
        String label10 = "label10";
        String label11 = "label11";
        String label12 = "label12";
        String label13 = "label13";
        String label14 = "label14";
        String label15 = "label15";
        String label16 = "label16";
        verticalStructure.get(comp1).get(epic1).get(story1).put(label1, new ArrayList<>());
        verticalStructure.get(comp1).get(epic1).get(story1).put(label2, new ArrayList<>());
        verticalStructure.get(comp1).get(epic1).get(story2).put(label3, new ArrayList<>());
        verticalStructure.get(comp1).get(epic1).get(story2).put(label4, new ArrayList<>());
        verticalStructure.get(comp1).get(epic2).get(story3).put(label5, new ArrayList<>());
        verticalStructure.get(comp1).get(epic2).get(story3).put(label6, new ArrayList<>());
        verticalStructure.get(comp1).get(epic2).get(story4).put(label7, new ArrayList<>());
        verticalStructure.get(comp1).get(epic2).get(story4).put(label8, new ArrayList<>());
        verticalStructure.get(comp2).get(epic3).get(story5).put(label9, new ArrayList<>());
        verticalStructure.get(comp2).get(epic3).get(story5).put(label10, new ArrayList<>());
        verticalStructure.get(comp2).get(epic3).get(story6).put(label11, new ArrayList<>());
        verticalStructure.get(comp2).get(epic3).get(story6).put(label12, new ArrayList<>());
        verticalStructure.get(comp2).get(epic4).get(story7).put(label13, new ArrayList<>());
        verticalStructure.get(comp2).get(epic4).get(story7).put(label14, new ArrayList<>());
        verticalStructure.get(comp2).get(epic4).get(story8).put(label15, new ArrayList<>());
        verticalStructure.get(comp2).get(epic4).get(story8).put(label16, new ArrayList<>());
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";
        String test5 = "test5";
        String test6 = "test6";
        String test7 = "test7";
        String test8 = "test8";
        String test9 = "test9";
        String test10 = "test10";
        String test11 = "test11";
        String test12 = "test12";
        String test13 = "test13";
        String test14 = "test14";
        String test15 = "test15";
        String test16 = "test16";
        verticalStructure.get(comp1).get(epic1).get(story1).get(label1).add(test1);
        verticalStructure.get(comp1).get(epic1).get(story1).get(label2).add(test2);
        verticalStructure.get(comp1).get(epic1).get(story2).get(label3).add(test3);
        verticalStructure.get(comp1).get(epic1).get(story2).get(label4).add(test4);
        verticalStructure.get(comp1).get(epic2).get(story3).get(label5).add(test5);
        verticalStructure.get(comp1).get(epic2).get(story3).get(label6).add(test6);
//        verticalStructure.get(comp1).get(epic2).get(story4).get(label7).add(test7);
        verticalStructure.get(comp1).get(epic2).get(story4).get(label8).add(test8);
        verticalStructure.get(comp2).get(epic3).get(story5).get(label9).add(test9);
        verticalStructure.get(comp2).get(epic3).get(story5).get(label10).add(test10);
        verticalStructure.get(comp2).get(epic3).get(story6).get(label11).add(test11);
        verticalStructure.get(comp2).get(epic3).get(story6).get(label12).add(test12);
        verticalStructure.get(comp2).get(epic4).get(story7).get(label13).add(test13);
        verticalStructure.get(comp2).get(epic4).get(story7).get(label14).add(test14);
//        verticalStructure.get(comp2).get(epic4).get(story8).get(label15).add(test15);
        verticalStructure.get(comp2).get(epic4).get(story7).get(label14).add(test16);
        verticalStructure.get(comp1).get(epic2).get(story4).get(label8).add(test16);
        verticalStructure.get(comp1).get(epic2).get(story3).get(label5).add(test12);
        verticalStructure.get(comp1).get(epic2).get(story3).get(label5).add(test16);
        return verticalStructure;
    }

}
