package testCases;

import configurations.BaseClass;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.CommonElements;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.Data.LoginData;

import static configurations.DriverManager.getDriver;
import static utilities.configProperties.getProperty;

@Epic("Regression Tests")
@Feature("User")
public class LogOutTestCase extends BaseClass {
    static LoginPage lp;
    static ProductsPage pp;
    static CommonElements ce;

    @Test(description = "Test Case: Logout Functionality Test")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify that the logout process works correctly")
    @Description("""
            1. Navigate to saucedemo.com.
            2. Enter valid credentials
            3. Username: standard_user
            4. Password: secret_sauce
            5. Verify that login was successful upon valid credentials
            6. Click the hamburger menu in the top-left corner
            7. Click on the logout button
            8. Verify that the user is redirected to the login page
            """)
    public static void logOutTestCase(){
        lp = new LoginPage(getDriver());
        pp = new ProductsPage(getDriver());
        ce = new CommonElements(getDriver());

        Object[][] validData = new LoginData().singleValidData();
        for (Object[] data : validData) {
            verifyValidCredentials((String) data[0], (String) data[1]);
        }
        verifyLogOutFunctionality();
    }

    @Step("Verify that login was successful upon valid credentials")
    public static void verifyValidCredentials(String username, String password){
        inputText(lp.usernameField, username);
        inputText(lp.passwordField, password);
        clickElement(lp.loginButton);

        waitForElementToBeVisible(ce.pageTitle,5);
        softAssert.assertTrue(ce.pageTitle.isDisplayed());
    }

    @Step("Verify that the user is redirected to the login page")
    public static void verifyLogOutFunctionality(){
        clickElement(pp.burgerMenuButton);
        waitForElementToBeVisible(pp.logoutButton,5);
        clickElement(pp.logoutButton);
        waitForElementToBeVisible(lp.swagLabLogo,5);
        softAssert.assertTrue(lp.swagLabLogo.isDisplayed());
        softAssert.assertEquals(getDriver().getCurrentUrl(), getProperty("base_url"));
    }
}
