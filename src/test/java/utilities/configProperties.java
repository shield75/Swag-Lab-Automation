package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class configProperties {
    private static final Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void writeEnvironmentProperties() {
        String browser = getProperty("browser");
        String os = System.getProperty("os.name");
        String javaVersion = System.getProperty("java.version");

        Properties envProps = new Properties();
        envProps.setProperty("Browser", browser != null ? browser : "unknown");
        envProps.setProperty("OS", os != null ? os : "unknown");
        envProps.setProperty("Java.Version", javaVersion != null ? javaVersion : "unknown");

        try {
            File resultsDir = new File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(new File(resultsDir, "environment.properties"));
            envProps.store(fos, "Allure Environment Properties");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write environment.properties for Allure");
        }
    }
}
