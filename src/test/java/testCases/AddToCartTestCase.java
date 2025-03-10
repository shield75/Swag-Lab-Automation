package testCases;

import pages.BasePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CommonElements;
import pages.LoginPage;
import pages.ProductsPage;
import utilities.Data.LoginData;

import static utilities.DriverManager.getDriver;

@Epic("Regression Tests")
@Feature("User")
public class AddToCartTestCase extends BasePage {
    static LoginPage lp;
    static ProductsPage pp;
    static CartPage cp;
    static CommonElements ce;
    public static int track = 0;

    @Test(description = "Test Case: Cart Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify that added products are visible in the cart page")
    @Description("""
            1. Navigate to saucedemo.com.
            2. Enter valid credentials
            3. Username: standard_user
            4. Password: secret_sauce
            5. Verify that login was successful upon valid credentials
            6. Add the first two products to the cart by clicking Add to cart button
            7. Verify that in the cart icon shows the correct number in the icon badge
            8. Remove the added items
            9. Verify that in the cart icon shows the correct number in the icon badge
            10. Add the first two products to the cart by clicking Add to cart button
            11. Click on the cart icon to go to the cart page
            12. Verify that added products are visible in the cart page
            """)
    public static void addToCartTestCase() throws InterruptedException {
        lp = new LoginPage(getDriver());
        pp = new ProductsPage(getDriver());
        cp = new CartPage(getDriver());
        ce = new CommonElements(getDriver());


        Object[][] validData = new LoginData().singleValidData();
        for (Object[] data : validData) {
            verifyValidCredentials((String) data[0], (String) data[1]);
        }
        verifyCartIconItems("Add", 2);
        verifyCartIconItems("Remove", 2);
        verifyCartIconItems("Add", 2);
        verifyProductsInCartPage();

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

    @Step("Verify that in the cart icon shows the correct number in the icon badge")
    public static void verifyCartIconItems(String action,  int numberOfItems) throws InterruptedException {

        if(action.equals("Add")){
            pp.addItemsToCart(numberOfItems);
            softAssert.assertEquals(pp.cartBadge.getText(), String.valueOf(numberOfItems), "Number of items did not match!");
            track = numberOfItems;
        }else {
            pp.addItemsToCart(numberOfItems);
            track = track - numberOfItems;

            if(track == 0){
               softAssert.assertFalse(verifyElementVisibility(pp.cartBadge), "Number of items did not match!");
            }
            else {
                softAssert.assertEquals(pp.cartBadge.getText(), String.valueOf(track), "Number of items did not match!");
            }
        }
    }

    @Step("Verify that added products are visible in the cart page")
    public static void verifyProductsInCartPage(){
        clickElement(pp.cartLink);
        waitForElementToBeVisible(ce.pageTitle, 5);
        softAssert.assertEquals(cp.cartItems.size(), track, "Number of items did not match in the cart page!");
    }
}
