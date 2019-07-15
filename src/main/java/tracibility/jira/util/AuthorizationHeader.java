package tracibility.jira.util;

import java.util.Base64;

import static tracibility.properties.Constants.TOKEN;
import static tracibility.properties.Constants.USERNAME;

public class AuthorizationHeader {

    private static String authString;

    private static void init() {
        String authValue = USERNAME + ":" + TOKEN;
        authString = "Basic " + Base64.getEncoder().encodeToString(authValue.getBytes());
    }

    public static String get() {
        if (authString == null) init();
        return authString;
    }

}
