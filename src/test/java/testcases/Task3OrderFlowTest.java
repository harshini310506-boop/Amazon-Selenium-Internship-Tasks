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

public class Task3OrderFlowTest extends TestBase {

    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        checkAllowedTime();

        initialization();

        wait = new WebDriverWait(
            getdriver(),
            Duration.ofSeconds(30)
        );
    }

    @Test
    public void searchAddToCartAndVerifyCheckout() {

        // ==================================================
        // 1. SEARCH FOR PRODUCT
        // ==================================================

        getdriver().get(
            "https://www.amazon.in/s?k=backpack"
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
        // 2. SELECT A PRODUCT
        // ==================================================

        List<WebElement> productTitles =
            getdriver().findElements(
                By.cssSelector(
                    "div[data-component-type='s-search-result'] h2 span"
                )
            );

        Assert.assertFalse(
            productTitles.isEmpty(),
            "No products were found."
        );

        WebElement selectedProduct = null;
        String selectedProductName = "";

        for (WebElement title : productTitles) {

            String productName =
                title.getText().trim();

            if (!productName.isEmpty()) {

                selectedProduct = title;
                selectedProductName = productName;

                break;
            }
        }

        Assert.assertNotNull(
            selectedProduct,
            "Product could not be selected."
        );

        System.out.println(
            "Selected Product: "
            + selectedProductName
        );

        // Open product page
        WebElement productLink =
            selectedProduct.findElement(
                By.xpath("./ancestor::a[1]")
            );

        String productUrl =
            productLink.getAttribute("href");

        Assert.assertNotNull(
            productUrl,
            "Product URL was not found."
        );

        getdriver().get(productUrl);

        // ==================================================
        // 3. VERIFY PRODUCT PAGE
        // ==================================================

        WebElement productTitle =
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.id("productTitle")
                )
            );

        Assert.assertFalse(
            productTitle.getText().trim().isEmpty(),
            "Product title is empty."
        );

        // ==================================================
        // 4. GET PRODUCT PRICE
        // ==================================================

        WebElement priceElement =
            wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(
                        "span.a-price-whole"
                    )
                )
            );

        String priceText =
            priceElement.getText().trim();

        // If normal text is empty, use textContent
        if (priceText.isEmpty()) {

            priceText =
                priceElement
                    .getAttribute("textContent")
                    .trim();
        }

        // Remove commas and non-numeric characters
        priceText =
            priceText.replace(",", "")
                     .replaceAll("[^0-9]", "")
                     .trim();

        Assert.assertFalse(
            priceText.isEmpty(),
            "Product price could not be read."
        );

        int productPrice =
            Integer.parseInt(priceText);

        System.out.println(
            "Product Price: ₹"
            + productPrice
        );

        // Task 3 requirement
        Assert.assertTrue(
            productPrice > 500,
            "Product/order value must be more than ₹500."
        );

        // ==================================================
        // 5. ADD PRODUCT TO CART
        // ==================================================

        WebElement addToCartButton =
            wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.id("add-to-cart-button")
                )
            );

        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            addToCartButton
        );

        addToCartButton.click();

        System.out.println(
            "Product added to cart."
        );

        // ==================================================
        // 6. OPEN CART
        // ==================================================

        List<WebElement> cartLinks =
            getdriver().findElements(
                By.id("nav-cart")
            );

        Assert.assertFalse(
            cartLinks.isEmpty(),
            "Cart button was not found."
        );

        cartLinks.get(0).click();

        wait.until(
            ExpectedConditions.urlContains(
                "/cart"
            )
        );

        System.out.println(
            "Shopping cart opened."
        );

        // ==================================================
        // 7. VERIFY CART TOTAL
        // ==================================================

        List<WebElement> cartPrices =
            getdriver().findElements(
                By.cssSelector(
                    "span.sc-price"
                )
            );

        Assert.assertFalse(
            cartPrices.isEmpty(),
            "Cart total was not displayed."
        );

        double cartTotal = 0;

        for (WebElement cartPrice : cartPrices) {

            String cartText =
                cartPrice.getText().trim();

            if (cartText.isEmpty()) {
                continue;
            }

            cartText =
                cartText.replace("₹", "")
                        .replace(",", "")
                        .replaceAll("[^0-9.]", "")
                        .trim();

            if (!cartText.isEmpty()) {

                cartTotal =
                    Double.parseDouble(cartText);

                break;
            }
        }

        Assert.assertTrue(
            cartTotal > 500,
            "Cart total must be more than ₹500."
        );

        System.out.println(
            "Cart Total: ₹"
            + cartTotal
        );

        // ==================================================
        // 8. PROCEED TO CHECKOUT
        // ==================================================

        List<WebElement> checkoutButtons =
            getdriver().findElements(
                By.name(
                    "proceedToRetailCheckout"
                )
            );

        if (checkoutButtons.isEmpty()) {

            checkoutButtons =
                getdriver().findElements(
                    By.xpath(
                        "//input[contains(" +
                        "translate(@value," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                        "'abcdefghijklmnopqrstuvwxyz')," +
                        "'proceed to checkout')]"
                    )
                );
        }

        Assert.assertFalse(
            checkoutButtons.isEmpty(),
            "Proceed to Checkout button was not found."
        );

        WebElement checkoutButton =
            checkoutButtons.get(0);

        ((JavascriptExecutor) getdriver()).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            checkoutButton
        );

        checkoutButton.click();

        System.out.println(
            "Proceeding to checkout."
        );

        // ==================================================
        // 9. VERIFY CHECKOUT/PAYMENT STAGE
        // ==================================================

        wait.until(
            ExpectedConditions.or(
                ExpectedConditions.urlContains("buy"),
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath(
                        "//*[contains(" +
                        "translate(normalize-space(.)," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                        "'abcdefghijklmnopqrstuvwxyz')," +
                        "'payment')]"
                    )
                )
            )
        );

        System.out.println(
            "Checkout/payment page reached successfully."
        );

        // IMPORTANT:
        // Do not submit real payment or place a real order.

        System.out.println(
            "Final payment/order submission was not performed."
        );

        System.out.println(
            "TASK 3 PASSED - Product search, cart, " +
            "order value and checkout flow verified."
        );
    }

    // ======================================================
    // TASK 3 TIME RESTRICTION: 6 PM - 7 PM
    // ======================================================

    private void checkAllowedTime() {

        LocalTime currentTime =
            LocalTime.now();

        LocalTime startTime =
            LocalTime.of(18, 0);

        LocalTime endTime =
            LocalTime.of(19, 0);

        if (currentTime.isBefore(startTime)
                || currentTime.isAfter(endTime)) {

            throw new SkipException(
                "Task 3 can run only between " +
                "6:00 PM and 7:00 PM. " +
                "Current time: " + currentTime
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