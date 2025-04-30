package logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoggerInitializer {

    public static void initLogging() {
        try {
            String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String fileName = "logs/out_" + dateStr + ".txt";
            FileOutputStream fos = new FileOutputStream(fileName, true); // append = true
            PrintStream ps = new PrintStream(fos, true, StandardCharsets.UTF_8);
            System.setOut(ps);
        } catch (IOException e) {
            System.err.println("Cannot init logging: " + e.getMessage());
        }
    }
}
