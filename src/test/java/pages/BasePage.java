package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import utilities.DriverManager;
import utilities.configProperties;

import java.time.Duration;
import java.util.*;

import static pages.AbstractPage.wait;
import static utilities.DriverManager.getDriver;


public class BasePage {
    public static SoftAssert softAssert = new SoftAssert();
    public static String browser = configProperties.getProperty("browser");

    @BeforeClass()
    @Step("Initializing WebDriver before test suite execution")
    public static void setUp() {
        DriverManager.initializeDriver(browser);

    }

    @AfterClass()
    @Step("Closing WebDriver after test suite execution")
    public static void tearDown() {
        DriverManager.quitDriver();
    }

    public static void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click the element: " + element + "\n" + e.getMessage());
        }
    }

    public static void inputText(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
            element.clear();
            element.sendKeys(text);
            Thread.sleep(1200);
        } catch (Exception e) {
            throw new RuntimeException("Failed to input text into the element: " + element + "\n" + e.getMessage());
        }
    }

    public static void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.visibilityOf(element));
    }

    public static void clickAndSelectOption(WebElement element, String optionText) {
        element.click();
        Select dropdown = new Select(element);

        List<WebElement> options = dropdown.getOptions();

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(optionText)) {
                dropdown.selectByVisibleText(optionText);
                break;
            }
        }
    }

    public static boolean verifyElementVisibility(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
