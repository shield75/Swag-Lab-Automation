package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends PageFactory {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='cart_item']")
    public List<WebElement> cartItems;

    @FindBy(xpath = "//button[@data-test='checkout']")
    public WebElement checkoutButton;

    @FindBy(xpath = "//button[starts-with(@data-test,'remove-')]")
    public List<WebElement> removeButtons;


}
