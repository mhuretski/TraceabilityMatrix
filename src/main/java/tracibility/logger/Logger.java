package tracibility.logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static tracibility.properties.Constants.*;

public class Logger {

    public static void logErrorMessage(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String error = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())
                + "FAIL\n" + sw.toString();
        File file = new File("log.txt");
        try {
            file.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                byte[] strToBytes = error.getBytes();
                outputStream.write(strToBytes);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void log(String message) {
        String messageToWrite = new SimpleDateFormat("HH:mm:ss").format(new Date())
                + SPACE
                + message
                + NEW_LINE;
        System.out.println(messageToWrite);
        File file = new File("log.txt");
        try {
            file.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
                byte[] strToBytes = messageToWrite.getBytes();
                outputStream.write(strToBytes);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
