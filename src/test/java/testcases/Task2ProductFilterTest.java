package testcases;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;

public class Task2ProductFilterTest extends TestBase {

    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        checkAllowedTime();

        initialization();

        wait = new WebDriverWait(getdriver(), Duration.ofSeconds(30));
    }

    @Test
    public void searchProductAndApplyFilters() {

        // Search specifically for a C-brand product
        getdriver().get(
            "https://www.amazon.in/s?k=CELLBELL+office+chair"
        );

        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    "div[data-component-type='s-search-result']"
                )
            )
        );

        System.out.println(
            "Amazon search results loaded."
        );

        // ==================================================
        // 1. APPLY BRAND FILTER - CELLBELL
        // ==================================================

        List<WebElement> brandFilters =
            getdriver().findElements(
                By.xpath(
                    "//span[contains(" +
                    "translate(normalize-space(.)," +
                    "'abcdefghijklmnopqrstuvwxyz'," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
                    "'CELLBELL')]"
                )
            );

        Assert.assertFalse(
            brandFilters.isEmpty(),
            "CELLBELL brand filter was not found."
        );

        WebElement brandFilter =
            brandFilters.get(0);

        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            brandFilter
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(
                brandFilter
            )
        );

        brandFilter.click();

        System.out.println(
            "Brand filter applied: CELLBELL"
        );

        // Wait for Amazon results to refresh
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    "div[data-component-type='s-search-result']"
                )
            )
        );

        // ==================================================
        // 2. APPLY PRICE FILTER - ABOVE ₹2,000
        // ==================================================

        List<WebElement> priceInputs =
            getdriver().findElements(
                By.xpath(
                    "//input[@id='low-price' or " +
                    "@name='low-price']"
                )
            );

        Assert.assertFalse(
            priceInputs.isEmpty(),
            "Minimum price input was not found."
        );

        WebElement minimumPriceBox =
            priceInputs.get(0);

        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            minimumPriceBox
        );

        // Amazon sometimes keeps this field in a state
        // where Selenium clear() is not allowed.
        // Set the value using JavaScript.
        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].value='2000';" +
            "arguments[0].dispatchEvent(new Event('input'," +
            "{bubbles:true}));" +
            "arguments[0].dispatchEvent(new Event('change'," +
            "{bubbles:true}));",
            minimumPriceBox
        );

        System.out.println(
            "Minimum price entered: ₹2,000"
        );

        // Find the Go button belonging to the price filter
        List<WebElement> goButtons =
            getdriver().findElements(
                By.xpath(
                    "//input[@type='submit' and " +
                    "(contains(@value,'Go') or " +
                    "contains(@aria-label,'Go'))]"
                )
            );

        Assert.assertFalse(
            goButtons.isEmpty(),
            "Price filter Go button was not found."
        );

        WebElement goButton =
            goButtons.get(0);

        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].click();",
            goButton
        );

        System.out.println(
            "Price filter applied: Above ₹2,000"
        );

        // Wait for results
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    "div[data-component-type='s-search-result']"
                )
            )
        );

        // ==================================================
        // 3. APPLY CUSTOMER RATING FILTER - 4 STARS & UP
        // ==================================================

        List<WebElement> ratingFilters =
            getdriver().findElements(
                By.xpath(
                    "//*[contains(" +
                    "translate(@aria-label," +
                    "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                    "'abcdefghijklmnopqrstuvwxyz')," +
                    "'4 stars & up')]"
                )
            );

        Assert.assertFalse(
            ratingFilters.isEmpty(),
            "4 Stars & Up rating filter was not found."
        );

        WebElement ratingFilter =
            ratingFilters.get(0);

        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            ratingFilter
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(
                ratingFilter
            )
        );

        ratingFilter.click();

        System.out.println(
            "Customer rating filter applied: 4 Stars & Up"
        );

        // Wait for filtered results
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    "div[data-component-type='s-search-result']"
                )
            )
        );

        // ==================================================
        // 4. VERIFY FINAL PRODUCT
        // ==================================================

        List<WebElement> products =
            getdriver().findElements(
                By.cssSelector(
                    "div[data-component-type='s-search-result']"
                )
            );

        Assert.assertFalse(
            products.isEmpty(),
            "No products found after applying filters."
        );

        boolean validProductFound = false;

        String selectedProductName = "";
        String selectedProductPrice = "";
        String selectedProductRating = "";

        for (WebElement product : products) {

            try {

                WebElement titleElement =
                    product.findElement(
                        By.cssSelector("h2 span")
                    );

                String productName =
                    titleElement.getText().trim();

                if (productName.isEmpty()) {
                    continue;
                }

                // Product must start with C
                String firstLetter =
                    productName.substring(0, 1)
                        .toUpperCase();

                if (!firstLetter.equals("C")) {
                    continue;
                }

                // Get price
                List<WebElement> prices =
                    product.findElements(
                        By.cssSelector(
                            "span.a-price-whole"
                        )
                    );

                if (prices.isEmpty()) {
                    continue;
                }

                String priceText =
                    prices.get(0).getText()
                        .replace(",", "")
                        .trim();

                int price =
                    Integer.parseInt(priceText);

                // Price must be ABOVE ₹2,000
                if (price <= 2000) {
                    continue;
                }

                // Get rating
                List<WebElement> ratings =
                    product.findElements(
                        By.cssSelector(
                            "[aria-label*='out of 5 stars']"
                        )
                    );

                if (ratings.isEmpty()) {
                    continue;
                }

                String ratingText =
                    ratings.get(0)
                        .getAttribute("aria-label");

                double rating =
                    Double.parseDouble(
                        ratingText.split(" ")[0]
                    );

                // Rating must be 4 or above
                if (rating < 4.0) {
                    continue;
                }

                // All conditions satisfied
                validProductFound = true;

                selectedProductName =
                    productName;

                selectedProductPrice =
                    priceText;

                selectedProductRating =
                    String.valueOf(rating);

                break;

            } catch (Exception e) {
                // Check next product
            }
        }

        // ==================================================
        // 5. FINAL VALIDATION
        // ==================================================

        Assert.assertTrue(
            validProductFound,
            "No C-starting product satisfied " +
            "all Task 2 conditions."
        );

        System.out.println(
            "Verified Product: "
            + selectedProductName
        );

        System.out.println(
            "Verified Price: ₹"
            + selectedProductPrice
        );

        System.out.println(
            "Verified Rating: "
            + selectedProductRating
        );

        System.out.println(
            "TASK 2 PASSED - Product filters verified."
        );
    }

    // ======================================================
    // TASK 2 TIME RESTRICTION
    // ======================================================

    private void checkAllowedTime() {

        LocalTime currentTime =
            LocalTime.now();

        LocalTime startTime =
            LocalTime.of(15, 0);

        LocalTime endTime =
            LocalTime.of(18, 0);

        if (currentTime.isBefore(startTime)
                || currentTime.isAfter(endTime)) {

            throw new SkipException(
                "Task 2 can run only between " +
                "3:00 PM and 6:00 PM. " +
                "Current time: " +
                currentTime
            );
        }
    }

    // ======================================================
    // CLOSE BROWSER
    // ======================================================

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}