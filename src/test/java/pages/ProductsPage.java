package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends PageFactory {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/div/div/div/div[2]/div/div/div/div/div[2]/div[2]/button")
    public List<WebElement> addProductButton;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    public WebElement cartBadge;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    public WebElement cartLink;

    @FindBy(xpath = "//select[@data-test='product-sort-container']")
    public WebElement sortSelect;

    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    public WebElement burgerMenuButton;

    @FindBy(xpath = "//a[@data-test='logout-sidebar-link']")
    public WebElement logoutButton;


    public void addItemsToCart(int numberOfItems) throws InterruptedException {
        for (int i = 0; i < numberOfItems; i++) {
            addProductButton.get(i).click();
            Thread.sleep(1200);
        }
    }

/**
     * Verifies that the visible product names are sorted according to the specified order.
     * <p>
     * Implementation details:
     * - Extracts the text from each provided WebElement and trims whitespace.
     * - Builds an expected list by sorting the actual names (case-insensitive) either ascending (A→Z)
     *   or descending (Z→A) based on the 'ascending' flag.
     * - Compares each position using TestNG SoftAssert so all mismatches are reported in one run.
     *
     * Notes:
     * - Assumes the list of WebElements reflects the current UI state (ensure any sorting action has completed).
     * - Case-insensitive comparison is used to avoid locale/case artifacts.
     * - Failures indicate the index where the order differs ("Mismatch at index i").
     *
     * @param nameElements the list of WebElements representing product name labels currently displayed on the page
     * @param ascending    true for A→Z, false for Z→A
     */
    public void assertSortedProductNames(List<WebElement> nameElements, boolean ascending) {
        SoftAssert softAssert = new SoftAssert();

        List<String> actualNames = nameElements.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());

        List<String> expectedNames = actualNames.stream()
                .sorted(ascending ? String::compareToIgnoreCase : (a, b) -> b.compareToIgnoreCase(a))
                .collect(Collectors.toList());

        for (int i = 0; i < actualNames.size(); i++) {
            softAssert.assertEquals(actualNames.get(i), expectedNames.get(i), "Mismatch at index " + i);
        }

        softAssert.assertAll();
    }

    public void assertSortedPrices(List<WebElement> priceElements, boolean ascending) {
        SoftAssert softAssert = new SoftAssert();
        List<String> actualPriceStrings = priceElements.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());

        List<Double> actualPrices = actualPriceStrings.stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .collect(Collectors.toList());

        List<Double> expectedPrices = actualPrices.stream()
                .sorted(ascending ? Double::compare : (a, b) -> Double.compare(b, a))
                .collect(Collectors.toList());

        for (int i = 0; i < actualPrices.size(); i++) {
            softAssert.assertEquals(actualPrices.get(i), expectedPrices.get(i), "Mismatch at index " + i);
        }

        softAssert.assertAll();
    }
}
