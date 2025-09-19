package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageFactory {
    public WebDriver driver;
    public static WebDriverWait wait;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.inItPage();
    }

    public void inItPage() {
        org.openqa.selenium.support.PageFactory.initElements(driver, this);
    }
}
