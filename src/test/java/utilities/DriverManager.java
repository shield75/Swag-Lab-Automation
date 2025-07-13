package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

import static utilities.configProperties.getProperty;

public class DriverManager {

    private static WebDriver driver;

    // Initialize the WebDriver based on the browser name.
    public static void initializeDriver(String browser) {
        if (driver == null) {  // If driver is not already initialized
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("-headless");  // Headless mode
                    // Additional Firefox options for CI can be added here if needed
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }

            // Set implicit wait and maximize window
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get(getProperty("base_url"));
        }
    }

    // Get the WebDriver instance
    public static WebDriver getDriver() {
        return driver;
    }

    // Quit the WebDriver and clean up
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;  // Clean up the driver instance
        }
    }
}
