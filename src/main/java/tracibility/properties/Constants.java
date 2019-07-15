package tracibility.properties;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {

    public static final String PATH_TO_PROPERTIES = "properties.properties";
    public static final String DOMAIN = "https://" + Properties.get().getProperty("jira") + ".atlassian.net";
    public static final String PROJECT = Properties.get().getProperty("project");
    public static final String USERNAME = Properties.get().getProperty("username");
    public static final String TOKEN = Properties.get().getProperty("token");
    public static final String PASSWORD = Properties.get().getProperty("password");
    public static final String FILTER = Properties.get().getProperty("filter");
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String SPRINT = (Properties.get().getProperty("sprint") != null &&
            !Properties.get().getProperty("sprint").equals(EMPTY_STRING)) ?
            Properties.get().getProperty("sprint") : EMPTY_STRING;
    public static final boolean IS_HEADLESS = Properties.get().getProperty("headless").equals("true");
    public static final String LINK_TO_JIRA = DOMAIN + "/browse/";
    public static final String LINK_TO_JIRA_COMPONENT =
            LINK_TO_JIRA + PROJECT + "-1?jql=project%3D" + PROJECT + "%20AND%20component%3D";
    public static final String PASS_TO_TEMP_FILES = System.getProperty("user.dir") + File.separator + "temp" + File.separator;
    public static final String PASS_TO_CHROME_DRIVER = "chromedriver.exe";
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss zzz uuuu";
    public static final String DEFAULT_LABEL = "другие";
    public static final String PASS_TO_EXPORT_RESULT =
            (Properties.get().getProperty("export") == null || Properties.get().getProperty("export").equals(EMPTY_STRING))
                    ? System.getProperty("user.dir") + "/export/"
                    : Properties.get().getProperty("export");
    public static final String EXCEL_FILE_NAME = "matrix "
            + ((SPRINT.equals(EMPTY_STRING)) ? EMPTY_STRING : (SPRINT + SPACE))
            + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())
            + ".xlsx";
    public static final int CELL_SIZE = 26;
    public static final String SHEET_NAME = "Matrix";

}
