package pages;

import configurations.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CommonElements extends BasePage {
    public CommonElements(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@class='title']")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[@data-test='inventory-item-name']")
    public List<WebElement> productNames;

    @FindBy(xpath = "//div[@data-test='inventory-item-price']")
    public List<WebElement> productPrices;
}
