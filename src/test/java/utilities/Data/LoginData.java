package utilities.Data;

import org.testng.annotations.DataProvider;

public class LoginData {

    public static final String STANDARD_USER = "standard_user";
    public static final String LOCKED_OUT_USER = "locked_out_user";
    public static final String PASSWORD = "secret_sauce";

    // Error messages
    public static final String ERROR_USERNAME_REQUIRED = "Epic sadface: Username is required";
    public static final String ERROR_PASSWORD_REQUIRED = "Epic sadface: Password is required";
    public static final String ERROR_INVALID_CREDENTIALS = "Epic sadface: Username and password do not match any user in this service";
    public static final String ERROR_USER_LOCKED_OUT = "Epic sadface: Sorry, this user has been locked out.";

    @DataProvider(name = "invalidData")
    public Object[][] invalidData() {
        return new Object[][]{
                {"", "", ERROR_USERNAME_REQUIRED},
                {"", PASSWORD, ERROR_USERNAME_REQUIRED},
                {STANDARD_USER, "", ERROR_PASSWORD_REQUIRED},
                {"username", "password", ERROR_INVALID_CREDENTIALS},
                {LOCKED_OUT_USER, PASSWORD, ERROR_USER_LOCKED_OUT}
        };
    }

    @DataProvider(name = "singleValidData")
    public Object[][] singleValidData() {
        return new Object[][]{
                {STANDARD_USER, PASSWORD}
        };
    }

    @DataProvider(name = "lockedOutUserData")
    public Object[][] lockedOutUserData() {
        return new Object[][]{
                {LOCKED_OUT_USER, PASSWORD, ERROR_USER_LOCKED_OUT}
        };
    }
}
