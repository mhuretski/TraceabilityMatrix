import tracibility.zephyr.file.entity.ExecutionStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TestFilesMerge {

    public static void main(String[] args) {
        Map<String, ExecutionStatus> map1 = new HashMap<>();
        Map<String, ExecutionStatus> map2 = new HashMap<>();

        ZonedDateTime executedOnDate1 = ZonedDateTime.parse("Fri Mar 29 07:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex1 = new ExecutionStatus(executedOnDate1, "PASS");
        String id1 = "DS5-1";
        map1.put(id1, ex1);
        ZonedDateTime executedOnDate2 = ZonedDateTime.parse("Fri Mar 29 07:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex2 = new ExecutionStatus(executedOnDate2, "PASS");
        String id2 = "DS5-2";
        map1.put(id2, ex2);
        ZonedDateTime executedOnDate3 = ZonedDateTime.parse("Fri Mar 29 07:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex3 = new ExecutionStatus(executedOnDate3, "PASS");
        String id3 = "DS5-3";
        map1.put(id3, ex3);
        ZonedDateTime executedOnDate4 = ZonedDateTime.parse("Fri Mar 29 07:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex4 = new ExecutionStatus(executedOnDate4, "PASS");
        String id4 = "DS5-4";
        map1.put(id4, ex4);
        ZonedDateTime executedOnDate9 = null;
        ExecutionStatus ex9 = new ExecutionStatus(executedOnDate9, "PASS");
        String id9 = "DS5-5";
        map1.put(id9, ex9);

        ZonedDateTime executedOnDate8 = ZonedDateTime.parse("Fri Mar 29 17:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex8 = new ExecutionStatus(executedOnDate8, "FAIL");
        String id8 = "DS5-2";
        map2.put(id8, ex8);
        ZonedDateTime executedOnDate5 = ZonedDateTime.parse("Fri Mar 29 01:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex5 = new ExecutionStatus(executedOnDate5, "FAIL");
        String id5 = "DS5-3";
        map2.put(id5, ex5);
        ZonedDateTime executedOnDate7 = null;
        ExecutionStatus ex7 = new ExecutionStatus(executedOnDate7, "FAIL");
        String id7 = "DS5-4";
        map2.put(id7, ex7);
        ZonedDateTime executedOnDate10 = ZonedDateTime.parse("Fri Mar 29 01:30:10 PDT 2019",
                DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss zzz uuuu", Locale.US));
        ExecutionStatus ex10 = new ExecutionStatus(executedOnDate10, "FAIL");
        String id10 = "DS5-5";
        ZonedDateTime executedOnDate6 = null;
        ExecutionStatus ex6 = new ExecutionStatus(executedOnDate6, "FAIL");
        String id6 = "DS5-6";
        map2.put(id6, ex6);
        map2.put(id10, ex10);

        Map<String, ExecutionStatus> map3 = new HashMap<>(map1);
        map2.forEach((key, value) ->
                map3.merge(key, value, (v1, v2) -> {
                    if (v1.getExecutedOnDate() == null) {
                        return v2;
                    } else if (v2.getExecutedOnDate() == null) {
                        return v1;
                    } else if (v1.getExecutedOnDate().isBefore(v2.getExecutedOnDate())) {
                        return v2;
                    } else return v1;
                }));
        System.out.println(map3);
        if (map3.get("DS5-1").getExecutedStatus().equals("PASS")
                && map3.get("DS5-2").getExecutedStatus().equals("FAIL")
                && map3.get("DS5-3").getExecutedStatus().equals("PASS")
                && map3.get("DS5-4").getExecutedStatus().equals("PASS")
                && map3.get("DS5-5").getExecutedStatus().equals("FAIL")
                && map3.get("DS5-6").getExecutedStatus().equals("FAIL"))
            System.out.println("test passed");
        else System.out.println("test failed");
    }

}
