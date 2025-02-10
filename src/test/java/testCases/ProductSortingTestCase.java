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
public class ProductSortingTestCase extends BaseClass {
    static LoginPage lp;
    static ProductsPage pp;
    static CommonElements ce;
    static final String optionLowToHigh = "Price (low to high)";
    static final String optionHighToLow = "Price (high to low)";
    static final String optionAtoZ = "Name (A to Z)";
    static final String optionZtoA = "Name (Z to A)";

    @Test(description = "Test Case: Sorting Products Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify the sorting functionality for products")
    @Description("""
            1. Navigate to saucedemo.com.
            2. Enter valid credentials
            3. Username: standard_user
            4. Password: secret_sauce
            5. Verify that login was successful upon valid credentials
            6. Click on the sorting dropdown field
            7. Select Price (low to high)
            8. Verify that the products are displayed in hte correct order based on the selected option
            9. Select Price (high to low)
            10. Verify that the products are displayed in hte correct order based on the selected option
            11. Select Name (A to Z)
            12. Verify that the products are displayed in hte correct order based on the selected option
            13. Select Name (Z to A)
            14. Verify that the products are displayed in hte correct order based on the selected option
            """)
    public static void productSortingTestCase() throws InterruptedException {
        lp = new LoginPage(getDriver());
        pp = new ProductsPage(getDriver());
        ce = new CommonElements(getDriver());

        Object[][] validData = new LoginData().singleValidData();
        for (Object[] data : validData) {
            verifyValidCredentials((String) data[0], (String) data[1]);
        }

        verifySortingProduct(optionLowToHigh);
        verifySortingProduct(optionHighToLow);
        verifySortingProduct(optionAtoZ);
        verifySortingProduct(optionZtoA);

        softAssert.assertAll();
    }

    @Step("Verify that login was successful upon valid credentials")
    public static void verifyValidCredentials(String username, String password){
        waitForElementToBeVisible(lp.swagLabLogo,5);

        inputText(lp.usernameField, username);
        inputText(lp.passwordField, password);
        clickElement(lp.loginButton);

        waitForElementToBeVisible(ce.pageTitle,5);
        softAssert.assertTrue(ce.pageTitle.isDisplayed());
    }

    @Step("Verify that the products are displayed in hte correct order based on the selected option")
    public static void verifySortingProduct(String option) throws InterruptedException {
        switch (option) {
            case optionLowToHigh -> {
                clickAndSelectOption(pp.sortSelect, option);
                Thread.sleep(5000);
                pp.assertSortedPrices(ce.productPrices, true);
            }
            case optionHighToLow -> {
                clickAndSelectOption(pp.sortSelect, option);
                Thread.sleep(1200);
                pp.assertSortedPrices(ce.productPrices, false);
            }
            case optionAtoZ -> {
                clickAndSelectOption(pp.sortSelect, option);
                Thread.sleep(1200);
                pp.assertSortedProductNames(ce.productNames, true);
            }
            case optionZtoA -> {
                clickAndSelectOption(pp.sortSelect, option);
                Thread.sleep(1200);
                pp.assertSortedProductNames(ce.productNames, false);
            }
        }
    }
}
