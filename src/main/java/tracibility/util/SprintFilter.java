package tracibility.util;

import org.json.JSONArray;
import org.json.JSONObject;

import static tracibility.properties.Constants.SPRINT;

public class SprintFilter {

    public static boolean isPresent(JSONObject fields) {
        JSONObject sprintObj = fields.optJSONObject("sprint");
        if (sprintObj != null && sprintObj.getString("name").equals(SPRINT))
            return true;

        JSONArray closedSprint = fields.optJSONArray("closedSprints");
        if (closedSprint != null) {
            for (int j = 0; j < closedSprint.length(); j++) {
                if (closedSprint.getJSONObject(j) != null
                        && closedSprint.getJSONObject(j).getString("name").equals(SPRINT))
                    return true;
            }
        }

        return false;
    }

}
