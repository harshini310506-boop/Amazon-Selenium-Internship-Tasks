package testcases;

import java.time.Duration;
import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;

public class Task6PriceDropNotificationTest extends TestBase {

    private WebDriverWait wait;

    @Test
    public void monitorProductPriceAndTriggerNotification() {

        initialization();

        wait = new WebDriverWait(
                getdriver(),
                Duration.ofSeconds(15)
        );

        System.out.println("========================================");
        System.out.println("TASK 6 - PRICE MONITORING & NOTIFICATION");
        System.out.println("========================================");

        String searchProduct = "backpack";

        double thresholdPrice = 1000.0;

        /*
         * Open Amazon.
         */
        getdriver().get(
                "https://www.amazon.in/"
        );

        /*
         * Search backpack.
         */
        WebElement searchBox =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.id("twotabsearchtextbox")
                        )
                );

        searchBox.clear();

        searchBox.sendKeys(
                searchProduct
        );

        searchBox.submit();

        /*
         * Wait for search results.
         */
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

        /*
         * Get search-result containers.
         */
        List<WebElement> results =
                getdriver().findElements(
                        By.cssSelector(
                                "div[data-component-type='s-search-result']"
                        )
                );

        Assert.assertFalse(
                results.isEmpty(),
                "No Amazon products found."
        );

        WebElement selectedResult = null;

        String productName = null;

        double currentPrice = 0;

        /*
         * Find first product that has
         * a readable name and price.
         */
        for (WebElement result : results) {

            try {

                WebElement title =
                        result.findElement(
                                By.cssSelector(
                                        "h2 span"
                                )
                        );

                String titleText =
                        title.getText().trim();

                if (titleText.isEmpty()) {
                    continue;
                }

                double price =
                        getPriceFromResult(
                                result
                        );

                if (price <= 0) {
                    continue;
                }

                selectedResult = result;

                productName = titleText;

                currentPrice = price;

                break;

            } catch (Exception ignored) {
            }
        }

        Assert.assertNotNull(
                selectedResult,
                "Could not find a product with a readable price."
        );

        System.out.println(
                "Selected Product: "
                        + productName
        );

        /*
         * Get ASIN from search result.
         */
        String asin =
                selectedResult.getAttribute(
                        "data-asin"
                );

        System.out.println(
                "Selected Product ASIN: "
                        + asin
        );

        /*
         * Actual Amazon search-result price.
         */
        System.out.println(
                "Current Price: ₹"
                        + currentPrice
        );

        System.out.println(
                "Price Threshold: ₹"
                        + thresholdPrice
        );

        /*
         * REAL PRICE CHECK
         */
        if (currentPrice < thresholdPrice) {

            System.out.println(
                    "Price Drop Detected: YES"
            );

            boolean notification =
                    showPriceDropNotification(
                            productName,
                            currentPrice,
                            thresholdPrice
                    );

            Assert.assertTrue(
                    notification,
                    "Price drop notification failed."
            );

            System.out.println(
                    "Notification: TRIGGERED"
            );

        } else {

            System.out.println(
                    "Price Drop Detected: NO"
            );

            System.out.println(
                    "Notification: Not triggered"
            );
        }

        /*
         * CONTROLLED NOTIFICATION TEST
         *
         * This verifies the notification mechanism
         * without pretending that Amazon actually
         * reduced the price.
         */
        System.out.println();

        System.out.println(
                "----------------------------------------"
        );

        System.out.println(
                "Testing Price Drop Notification Mechanism"
        );

        double controlledTestPrice = 999.0;

        System.out.println(
                "Controlled Test Price: ₹"
                        + controlledTestPrice
        );

        if (controlledTestPrice <
                thresholdPrice) {

            System.out.println(
                    "Controlled Price Drop Detected: YES"
            );

            boolean notification =
                    showPriceDropNotification(
                            productName,
                            controlledTestPrice,
                            thresholdPrice
                    );

            Assert.assertTrue(
                    notification,
                    "Controlled notification failed."
            );

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "PRICE DROP NOTIFICATION"
            );

            System.out.println(
                    "Product: "
                            + productName
            );

            System.out.println(
                    "Controlled Price: ₹"
                            + controlledTestPrice
            );

            System.out.println(
                    "Threshold Price: ₹"
                            + thresholdPrice
            );

            System.out.println(
                    "Notification Status: TRIGGERED"
            );

            System.out.println(
                    "Notification Mechanism: VERIFIED"
            );

            System.out.println(
                    "========================================"
            );
        }

        System.out.println();

        System.out.println(
                "TASK 6 PASSED - Product price monitoring and notification mechanism verified."
        );
    }


    /*
     * Read price directly from an Amazon
     * search-result container.
     */
    private double getPriceFromResult(
            WebElement result) {

        try {

            /*
             * First try a-price-whole.
             */
            List<WebElement> wholePrices =
                    result.findElements(
                            By.cssSelector(
                                    "span.a-price-whole"
                            )
                    );

            for (WebElement price :
                    wholePrices) {

                String text =
                        price.getText();

                if (text == null ||
                        text.trim().isEmpty()) {

                    continue;
                }

                String cleaned =
                        text.replaceAll(
                                "[^0-9.]",
                                ""
                        );

                if (!cleaned.isEmpty()) {

                    return Double.parseDouble(
                            cleaned
                    );
                }
            }

            /*
             * Second option:
             * a-price.
             */
            List<WebElement> prices =
                    result.findElements(
                            By.cssSelector(
                                    "span.a-price"
                            )
                    );

            for (WebElement price :
                    prices) {

                String text =
                        price.getText();

                if (text == null ||
                        text.trim().isEmpty()) {

                    continue;
                }

                String cleaned =
                        text.replaceAll(
                                "[^0-9.]",
                                ""
                        );

                if (!cleaned.isEmpty()) {

                    return Double.parseDouble(
                            cleaned
                    );
                }
            }

        } catch (Exception ignored) {
        }

        return 0;
    }


    /*
     * Desktop price-drop notification.
     */
    private boolean showPriceDropNotification(
            String productName,
            double currentPrice,
            double thresholdPrice) {

        try {

            System.out.println();

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "PRICE DROP NOTIFICATION"
            );

            System.out.println(
                    "========================================"
            );

            System.out.println(
                    "Product: "
                            + productName
            );

            System.out.println(
                    "Current Price: ₹"
                            + currentPrice
            );

            System.out.println(
                    "Threshold Price: ₹"
                            + thresholdPrice
            );

            System.out.println(
                    "Notification Status: TRIGGERED"
            );

            System.out.println(
                    "========================================"
            );

            /*
             * Display actual notification popup.
             */
            JOptionPane.showMessageDialog(
                    null,
                    "Product: "
                            + productName
                            + "\n\nCurrent Price: ₹"
                            + currentPrice
                            + "\nThreshold Price: ₹"
                            + thresholdPrice
                            + "\n\nPrice has dropped below the configured threshold.",
                    "Amazon Price Drop Alert",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Notification failed: "
                            + e.getMessage()
            );

            return false;
        }
    }


    /*
     * Close browser.
     */
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