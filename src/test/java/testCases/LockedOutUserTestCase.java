package testCases;

import configurations.BaseClass;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.CommonElements;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.Data.LoginData;

import static configurations.DriverManager.getDriver;

@Epic("Regression Tests")
@Feature("User")
public class LockedOutUserTestCase extends BaseClass {
    static LoginPage lp;
    static ProductsPage pp;
    static CommonElements ce;

    @Test(description = "Locked-Out User Validation ")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Ensure that a locked-out user cannot log in.")
    @Description("""
            1. Navigate to saucedemo.com.
            2. Enter the locked out credentials:
            3. Username: locked_out_user
            4. Password: secret_sauce
            5. Click on the login button
            6. Verify the error message: “Sorry, this user has been locked out.”
            """)
    public static void lockedOutUserTestCase(){
        lp = new LoginPage(getDriver());
        pp = new ProductsPage(getDriver());
        ce = new CommonElements(getDriver());

        Object[][] lockedOutUserData = new LoginData().lockedOutUserData();
        for (Object[] data : lockedOutUserData) {
            verifyInvalidCredentials((String) data[0], (String) data[1], (String) data[2]);
        }
        softAssert.assertAll();
    }

    @Step("Verify the error message: “Sorry, this user has been locked out.”")
    public static void verifyInvalidCredentials(String username, String password, String errorMessage){
        waitForElementToBeVisible(lp.swagLabLogo,5);

        inputText(lp.usernameField, username);
        inputText(lp.passwordField, password);
        clickElement(lp.loginButton);

        waitForElementToBeVisible(lp.errorMessage,5);
        softAssert.assertEquals(lp.errorMessage.getText(), errorMessage);
        clickElement(lp.errorMessageButton);
    }
}
