package automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties p = new Properties();
    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            p.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return p.getProperty(key);
    }

    public static int timeoutMs() {
        return Integer.parseInt(get("timeout.ms"));
    }

    public static String authToken() {
        String env = System.getProperty("AUTH_TOKEN");
        return (env != null && !env.isEmpty()) ? env : get("auth.token");
    }
}
