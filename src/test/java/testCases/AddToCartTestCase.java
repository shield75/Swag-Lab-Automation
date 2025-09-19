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
    static int numberOfItems = 3;

    @Test(description = "Test Case: Cart Page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Verify that added products are visible in the cart page")
    @Description(value = """
            1. Navigate to saucedemo.com.
            2. Enter valid credentials
            3. Username: standard_user
            4. Password: secret_sauce
            5. Verify that login was successful upon valid credentials
            6. Add the three products to the cart by clicking Add to cart button
            7. Verify that in the cart icon shows the correct number in the icon badge
            8. Click on the cart icon to go to the cart page
            9. Verify that added products are visible in the cart page
            9. Remove the second item
            10. Verify that the second item has been removed and cart icon number has been updated
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
        verifyCartIconItems("Add", numberOfItems);
        verifyProductsInCartPage();
        verifyRemoveProduct(2);

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

    @Step(" Verify that the second item has been removed and cart icon number has been updated")
    public static void verifyRemoveProduct(int itemNumber){
        clickElement(cp.removeButtons.get(itemNumber));
        softAssert.assertEquals(cp.cartItems.size(), track - 1, "Item was not removed!");
        track = track - 1;
        if(track == 0){
            softAssert.assertFalse(verifyElementVisibility(pp.cartBadge), "Number of items did not match!");
        }
        else {
            softAssert.assertEquals(pp.cartBadge.getText(), String.valueOf(track), "Number of items did not match!");
        }

    }
}
