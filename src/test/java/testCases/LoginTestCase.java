package testCases;

import pages.BasePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.CommonElements;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.Data.LoginData;

import static utilities.DriverManager.getDriver;

@Epic("Regression Tests")
@Feature("User")
public class LoginTestCase extends BasePage {
    static LoginPage lp;
    static ProductsPage pp;
    static CommonElements ce;

    @Test(description = "Test Case: Login Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify login with valid and invalid credentials.")
    @Description("""
            1. Navigate to saucedemo.com.
            2. Enter invalid credentials (wrong username/password)
            3. Verify that error message is showing upon invalid credentials
            4. Enter valid credentials
            5. Username: standard_user
            6. Password: secret_sauce
            7. Verify that login was successful upon valid credentials
            """)
    public static void loginTestCase(){
        lp = new LoginPage(getDriver());
        pp = new ProductsPage(getDriver());
        ce = new CommonElements(getDriver());

        // Run invalid credential tests
        Object[][] invalidData = new LoginData().invalidData();
        for (Object[] data : invalidData) {
            verifyInvalidCredentials((String) data[0], (String) data[1], (String) data[2]);
        }

        // Run valid credential test
        Object[][] validData = new LoginData().singleValidData();
        for (Object[] data : validData) {
            verifyValidCredentials((String) data[0], (String) data[1]);
        }

        softAssert.assertAll();
    }

    @Step("Verify that error message is showing upon invalid credentials")
    public static void verifyInvalidCredentials(String username, String password, String errorMessage){
        waitForElementToBeVisible(lp.swagLabLogo,5);

        inputText(lp.usernameField, username);
        inputText(lp.passwordField, password);
        clickElement(lp.loginButton);

        waitForElementToBeVisible(lp.errorMessage,5);
        softAssert.assertEquals(lp.errorMessage.getText(), errorMessage);
        clickElement(lp.errorMessageButton);

    }

    @Step("Verify that login was successful upon valid credentials")
    public static void verifyValidCredentials(String username, String password){
        inputText(lp.usernameField, username);
        inputText(lp.passwordField, password);
        clickElement(lp.loginButton);

        waitForElementToBeVisible(ce.pageTitle,5);
        softAssert.assertTrue(ce.pageTitle.isDisplayed());
    }

}
