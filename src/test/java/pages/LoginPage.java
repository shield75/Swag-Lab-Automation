package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='login_logo']")
    public WebElement swagLabLogo;

    @FindBy(xpath = "//div[@class='login-box']//form/div[1]/input")
    public WebElement usernameField;

    @FindBy(xpath = "//div[@class='login-box']//form/div[2]/input")
    public WebElement passwordField;

    @FindBy(xpath = "//button[@class='error-button']")
    public WebElement errorMessageButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    public WebElement errorMessage;


    @FindBy(xpath = "//input[@id='login-button']")
    public WebElement loginButton;
}
