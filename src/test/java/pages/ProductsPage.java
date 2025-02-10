package pages;

import configurations.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {

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
