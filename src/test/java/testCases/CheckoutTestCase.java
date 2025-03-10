package testCases;

import pages.BasePage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.*;
import utilities.Data.CheckoutData;
import utilities.Data.LoginData;

import java.util.ArrayList;
import java.util.List;

import static utilities.DriverManager.getDriver;

@Epic("Regression Tests")
@Feature("User")
public class CheckoutTestCase extends BasePage {
    static LoginPage lp;
    static ProductsPage pp;
    static CartPage cp;
    static CheckoutPage co;
    static CommonElements ce;
    static int numberOfItems = 1;
    static List<String> itemNames;

    @Test(description = "Test Case: Checkout Process Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Automate the full checkout process")
    @Description("""
            1. Navigate to saucedemo.com.
            2. Enter valid credentials
            3. Username: standard_user
            4. Password: secret_sauce
            5. Verify that login was successful upon valid credentials
            6. Add the first two products to the cart by clicking Add to cart button
            7. Verify that in the cart icon shows the correct number in the icon badge
            8. Click on the cart icon to go to the cart page
            9. Verify that added products are visible in the cart page
            10. Click on the checkout button
            11. Enter the checkout information (First Name: John, Last Name: Doe, Postal Code: 12345)
            12. Click on the continue button
            13. Verify that correct item is showing in the order summary
            14. Click on the finish button
            15. Verify that the success message is showing after finishing the order
            """)
    public static void checkoutTestCase() throws InterruptedException {
        lp = new LoginPage(getDriver());
        pp = new ProductsPage(getDriver());
        cp = new CartPage(getDriver());
        co = new CheckoutPage(getDriver());
        ce = new CommonElements(getDriver());
        itemNames = new ArrayList<>();

        Object[][] validData = new LoginData().singleValidData();
        for (Object[] data : validData) {
            verifyValidCredentials((String) data[0], (String) data[1]);
        }
        verifyCartIconNumber();
        verifyProductsInCartPage();

        Object[][] checkoutData = new CheckoutData().checkoutData();
        for (Object[] data : checkoutData) {
            verifyCorrectDataInCheckoutPage((String) data[0], (String) data[1], (String)data[2]);
        }

        verifySuccessMessage();
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
    public static void verifyCartIconNumber() throws InterruptedException {
        pp.addItemsToCart(numberOfItems);
        for (int i = 0; i <numberOfItems; i++) {
            itemNames.add(ce.productNames.get(i).getText());
        }
        softAssert.assertEquals(pp.cartBadge.getText(), String.valueOf(numberOfItems), "Number of items did not match!");
    }

    @Step("Verify that added products are visible in the cart page")
    public static void verifyProductsInCartPage(){
        clickElement(pp.cartLink);
        waitForElementToBeVisible(ce.pageTitle, 5);

        for (int i = 0; i <numberOfItems; i++) {
            softAssert.assertEquals(ce.productNames.get(i).getText(), itemNames.get(i), "Item names did not match!");
        }
    }

    @Step("Verify that correct item is showing in the order summary")
    public static void verifyCorrectDataInCheckoutPage(String firstName, String lastName, String postalCode){
        clickElement(cp.checkoutButton);
        waitForElementToBeVisible(ce.pageTitle,5);

        inputText(co.checkoutForm.get(0), firstName);
        inputText(co.checkoutForm.get(1), lastName);
        inputText(co.checkoutForm.get(2), postalCode);

        clickElement(co.checkoutForm.get(3));

        waitForElementToBeVisible(ce.pageTitle, 5);
        for (int i = 0; i <numberOfItems; i++) {
            softAssert.assertEquals(ce.productNames.get(i).getText(), itemNames.get(i), "Item names did not match!");
        }
    }

    @Step("Verify that the success message is showing after finishing the order")
    public static void verifySuccessMessage(){
        clickElement(co.finishButton);
        waitForElementToBeVisible(co.completeHeader,5);
        softAssert.assertEquals(co.completeHeader.getText(), "Thank you for your order!");
    }

}
