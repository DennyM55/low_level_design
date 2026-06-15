package _02_singleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApplicationLogger {
    private static final ApplicationLogger INSTANCE = new ApplicationLogger();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ApplicationLogger() {
    }

    public static ApplicationLogger getInstance() {
        return INSTANCE;
    }

    public void log(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println(timestamp + " | " + message);
    }
}
