package testcases;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;

public class Task5MultipleProductsCartTest extends TestBase {

    private WebDriverWait wait;

    /*
     * All search names below contain exactly 10 alphabetic characters.
     */
    private final List<String> productNames = Arrays.asList(
            "headphones",
            "calculator",
            "smartwatch",
            "sunglasses",
            "toothbrush",
            "microphone",
            "stationery",
            "tablecloth",
            "lunchboxes",
            "pillowcase"
    );

    @Test
    public void multipleProductsCartTest() {

        // Task 5 allowed testing time: 6 PM - 7 PM
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(18, 0);
        LocalTime end = LocalTime.of(19, 0);

        Assert.assertTrue(
                !now.isBefore(start) && now.isBefore(end),
                "TASK 5 can be tested only between 6 PM and 7 PM."
        );

        initialization();

        wait = new WebDriverWait(getdriver(), Duration.ofSeconds(15));

        System.out.println("======================================");
        System.out.println("TASK 5 - MULTIPLE PRODUCTS CART TEST");
        System.out.println("======================================");

        /*
         * Validate the task's search-name condition.
         */
        for (String name : productNames) {

            Assert.assertTrue(
                    name.matches("[A-Za-z]{10}"),
                    "Invalid product name: " + name
            );

            System.out.println(
                    "Validated Product Name: " + name +
                    " (" + name.length() + " characters)"
            );
        }

        int successfulFirstProduct = -1;

        /*
         * Try different products until one can actually remain
         * in the Amazon cart.
         */
        for (int i = 0; i < productNames.size(); i++) {

            String firstProduct = productNames.get(i);

            try {

                clearCart();

                System.out.println();
                System.out.println(
                        "Trying FIRST product: " + firstProduct
                );

                searchProduct(firstProduct);

                if (!selectFirstAvailableProduct()) {
                    System.out.println(
                            "No selectable product found for: "
                                    + firstProduct
                    );
                    continue;
                }

                if (!addCurrentProductToCart()) {
                    System.out.println(
                            "Could not add first product: "
                                    + firstProduct
                    );
                    continue;
                }

                int count = getCartCount();

                System.out.println(
                        "Cart count after first product: " + count
                );

                if (count >= 1) {
                    successfulFirstProduct = i;
                    break;
                }

            } catch (Exception e) {

                System.out.println(
                        "First product failed: " +
                        firstProduct
                );

                System.out.println(
                        "Reason: " + e.getMessage()
                );
            }
        }

        Assert.assertTrue(
                successfulFirstProduct >= 0,
                "Could not add a valid first product to Amazon cart."
        );

        /*
         * Now try different second products.
         */
        boolean secondProductAdded = false;

        for (int j = 0; j < productNames.size(); j++) {

            if (j == successfulFirstProduct) {
                continue;
            }

            String secondProduct = productNames.get(j);

            try {

                int before = getCartCount();

                System.out.println();
                System.out.println(
                        "Trying SECOND product: " + secondProduct
                );

                /*
                 * If the first product disappeared, restore it.
                 */
                if (before < 1) {

                    System.out.println(
                            "First product disappeared. Restoring cart."
                    );

                    String firstProduct =
                            productNames.get(successfulFirstProduct);

                    searchProduct(firstProduct);

                    if (!selectFirstAvailableProduct()
                            || !addCurrentProductToCart()) {

                        System.out.println(
                                "Could not restore first product."
                        );
                        continue;
                    }

                    before = getCartCount();
                }

                searchProduct(secondProduct);

                if (!selectFirstAvailableProduct()) {

                    System.out.println(
                            "No selectable product found for: "
                                    + secondProduct
                    );
                    continue;
                }

                boolean added = addCurrentProductToCart();

                if (!added) {

                    System.out.println(
                            "Second product was not accepted by Amazon: "
                                    + secondProduct
                    );

                    continue;
                }

                int after = getCartCount();

                System.out.println(
                        "Cart count before second product: "
                                + before
                );

                System.out.println(
                        "Cart count after second product: "
                                + after
                );

                if (after >= 2) {

                    secondProductAdded = true;

                    System.out.println(
                            "Second product successfully retained in cart."
                    );

                    break;
                }

            } catch (Exception e) {

                System.out.println(
                        "Second product failed: "
                                + secondProduct
                );

                System.out.println(
                        "Reason: "
                                + e.getMessage()
                );
            }
        }

        Assert.assertTrue(
                secondProductAdded,
                "Amazon did not retain at least two products in the cart."
        );

        /*
         * Open final cart and verify.
         */
        getdriver().get("https://www.amazon.in/gp/cart/view.html");

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div.sc-list-item")
                )
        );

        List<WebElement> cartItems =
                getdriver().findElements(
                        By.cssSelector("div.sc-list-item")
                );

        System.out.println();
        System.out.println(
                "Final Cart Items: " + cartItems.size()
        );

        Assert.assertTrue(
                cartItems.size() >= 2,
                "At least two products must be present in cart."
        );

        /*
         * Verify cart total.
         */
        double total = getCartTotal();

        System.out.println(
                "Final Cart Total: ₹" + total
        );

        Assert.assertTrue(
                total > 2000,
                "Cart total must be greater than ₹2,000."
        );

        System.out.println();
        System.out.println(
                "TASK 5 PASSED - Multiple products added and total verified."
        );
    }


    /*
     * Search product from Amazon home page.
     */
    private void searchProduct(String productName) {

        getdriver().get("https://www.amazon.in/");

        WebElement searchBox =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.id("twotabsearchtextbox")
                        )
                );

        searchBox.clear();
        searchBox.sendKeys(productName);

        searchBox.submit();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(
                                "div[data-component-type='s-search-result']"
                        )
                )
        );

        System.out.println(
                "Amazon search results loaded for: "
                        + productName
        );
    }


    /*
     * Select the first usable Amazon search result.
     */
    private boolean selectFirstAvailableProduct() {

        List<WebElement> results =
                getdriver().findElements(
                        By.cssSelector(
                                "div[data-component-type='s-search-result'] h2 span"
                        )
                );

        if (results.isEmpty()) {
            return false;
        }

        for (WebElement result : results) {

            try {

                String title = result.getText();

                if (title == null || title.trim().isEmpty()) {
                    continue;
                }

                WebElement link =
                        result.findElement(
                                By.xpath("./ancestor::a[1]")
                        );

                String href = link.getAttribute("href");

                if (href == null || href.isEmpty()) {
                    continue;
                }

                System.out.println(
                        "Selected Amazon Product: "
                                + title
                );

                /*
                 * Open product page directly.
                 */
                ((JavascriptExecutor) getdriver())
                        .executeScript(
                                "window.location.href=arguments[0];",
                                href
                        );

                wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                By.id("productTitle")
                        )
                );

                return true;

            } catch (Exception e) {
                // Try next search result.
            }
        }

        return false;
    }


    /*
     * Add current product.
     *
     * First tries the normal Amazon product-page button.
     */
    private boolean addCurrentProductToCart() {

        int before = getCartCount();

        System.out.println(
                "Cart count before adding: " + before
        );

        try {

            WebElement addButton =
                    wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                    By.id("add-to-cart-button")
                            )
                    );

            ((JavascriptExecutor) getdriver())
                    .executeScript(
                            "arguments[0].click();",
                            addButton
                    );

            System.out.println(
                    "Amazon Add to Cart button clicked."
            );

        } catch (Exception e) {

            System.out.println(
                    "Normal Add to Cart button failed."
            );

            return false;
        }

        /*
         * Give Amazon a short period to update the cart.
         */
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        closeAmazonPopups();

        int after = getCartCount();

        System.out.println(
                "Cart count after adding: " + after
        );

        /*
         * The important verification:
         * the Amazon cart count must actually increase.
         */
        return after > before;
    }


    /*
     * Read Amazon cart count.
     */
    private int getCartCount() {

        try {

            WebElement cartCount =
                    getdriver().findElement(
                            By.id("nav-cart-count")
                    );

            String text = cartCount.getText();

            if (text == null || text.trim().isEmpty()) {
                return 0;
            }

            String number =
                    text.replaceAll("[^0-9]", "");

            if (number.isEmpty()) {
                return 0;
            }

            return Integer.parseInt(number);

        } catch (Exception e) {

            return 0;
        }
    }


    /*
     * Get cart subtotal.
     */
    private double getCartTotal() {

        List<By> totalLocators = Arrays.asList(

                By.cssSelector(
                        "span#sc-subtotal-amount-activecart span.a-price-whole"
                ),

                By.cssSelector(
                        "span.sc-price"
                ),

                By.cssSelector(
                        "span.a-price-whole"
                )
        );

        for (By locator : totalLocators) {

            try {

                List<WebElement> elements =
                        getdriver().findElements(locator);

                for (WebElement element : elements) {

                    String text = element.getText();

                    if (text == null || text.trim().isEmpty()) {
                        continue;
                    }

                    String cleaned =
                            text.replaceAll("[^0-9.]", "");

                    if (!cleaned.isEmpty()) {

                        double value =
                                Double.parseDouble(cleaned);

                        if (value > 0) {
                            return value;
                        }
                    }
                }

            } catch (Exception e) {
                // Try next locator.
            }
        }

        return 0;
    }


    /*
     * Remove existing cart contents before trying a new first product.
     */
    private void clearCart() {

        try {

            getdriver().get(
                    "https://www.amazon.in/gp/cart/view.html"
            );

            Thread.sleep(1000);

            List<WebElement> deleteButtons =
                    getdriver().findElements(
                            By.cssSelector(
                                    "input[value='Delete']"
                            )
                    );

            for (WebElement delete : deleteButtons) {

                try {
                    ((JavascriptExecutor) getdriver())
                            .executeScript(
                                    "arguments[0].click();",
                                    delete
                            );

                    Thread.sleep(500);

                } catch (Exception ignored) {
                }
            }

        } catch (Exception ignored) {
        }
    }


    /*
     * Close common Amazon popups if they appear.
     */
    private void closeAmazonPopups() {

        String[] selectors = {

                "#attach-warranty-close",
                "#attach-close_sideSheet-link",
                "button[aria-label='Close']",
                ".a-popover-header .a-button-close"
        };

        for (String selector : selectors) {

            try {

                List<WebElement> elements =
                        getdriver().findElements(
                                By.cssSelector(selector)
                        );

                for (WebElement element : elements) {

                    if (element.isDisplayed()) {

                        ((JavascriptExecutor) getdriver())
                                .executeScript(
                                        "arguments[0].click();",
                                        element
                                );

                        Thread.sleep(300);
                    }
                }

            } catch (Exception ignored) {
            }
        }
    }


    @AfterMethod
    public void tearDown() {

        try {

            if (getdriver() != null) {
                getdriver().quit();
            }

        } catch (Exception ignored) {
        }
    }
}