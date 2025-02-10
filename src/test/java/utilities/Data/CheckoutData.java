package utilities.Data;

import org.testng.annotations.DataProvider;

public class CheckoutData {
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "DOE";
    public static final String POSTAL_CODE = "12345";

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return new Object[][]{
                {FIRST_NAME, LAST_NAME, POSTAL_CODE}
        };
    }
}
