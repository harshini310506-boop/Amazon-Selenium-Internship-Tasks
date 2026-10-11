package testcases;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;

public class Task1ProductDetailsTest extends TestBase {

    WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        checkAllowedTime();

        initialization();

        wait = new WebDriverWait(getdriver(), Duration.ofSeconds(30));
    }

    @Test
    public void selectProductAndVerifyProductDetails() {

        // Amazon Books search - non-electronic product
        getdriver().get(
            "https://www.amazon.in/s?k=Atomic+Habits&i=stripbooks"
        );

        // Wait for search results
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    "div[data-component-type='s-search-result']"
                )
            )
        );

        // Get product titles
        List<WebElement> productTitles =
            getdriver().findElements(
                By.cssSelector(
                    "div[data-component-type='s-search-result'] h2 span"
                )
            );

        WebElement selectedProductTitle = null;
        String selectedProductName = "";

        // Find product beginning with A, B, C or D
        for (WebElement title : productTitles) {

            String productName = title.getText().trim();

            if (productName.isEmpty()) {
                continue;
            }

            System.out.println("Product Found: " + productName);

            String firstLetter =
                productName.substring(0, 1).toUpperCase();

            if (firstLetter.matches("[ABCD]")) {

                selectedProductTitle = title;
                selectedProductName = productName;

                break;
            }
        }

        // Verify product was found
        Assert.assertNotNull(
            selectedProductTitle,
            "No product starting with A, B, C or D was found."
        );

        System.out.println(
            "Selected Product: " + selectedProductName
        );

        // Find the clickable product link from the title
        WebElement productLink =
            selectedProductTitle.findElement(
                By.xpath("./ancestor::a[1]")
            );

        String productUrl =
            productLink.getAttribute("href");

        Assert.assertNotNull(
            productUrl,
            "Product URL was not found."
        );

        System.out.println(
            "Product URL: " + productUrl
        );

        // Open selected product page
        getdriver().get(productUrl);

        // Wait for product title
        WebElement productTitle =
            wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.id("productTitle")
                )
            );

        String actualProductTitle =
            productTitle.getText().trim();

        System.out.println(
            "Product Page Title: " + actualProductTitle
        );

        // Verify product title
        Assert.assertFalse(
            actualProductTitle.isEmpty(),
            "Product title is empty."
        );

        // Verify product name starts with A, B, C or D
        String productPageFirstLetter =
            actualProductTitle.substring(0, 1).toUpperCase();

        Assert.assertTrue(
            productPageFirstLetter.matches("[ABCD]"),
            "Product page title does not start with A, B, C or D."
        );

        // Get product price
        List<WebElement> priceElements =
            getdriver().findElements(
                By.cssSelector("span.a-price-whole")
            );

        Assert.assertFalse(
            priceElements.isEmpty(),
            "Product price is not displayed."
        );

        String productPrice =
            priceElements.get(0).getText().trim();

        Assert.assertFalse(
            productPrice.isEmpty(),
            "Product price is empty."
        );

        System.out.println(
            "Verified Product Name: "
            + actualProductTitle
        );

        System.out.println(
            "Verified Product Price: ₹"
            + productPrice
        );

        System.out.println(
            "TASK 1 PASSED - Product selected and details verified."
        );
    }

    private void checkAllowedTime() {

        LocalTime currentTime = LocalTime.now();

        LocalTime startTime =
            LocalTime.of(15, 0);

        LocalTime endTime =
            LocalTime.of(18, 0);

        if (currentTime.isBefore(startTime)
                || currentTime.isAfter(endTime)) {

            throw new SkipException(
                "Task 1 can run only between 3:00 PM and 6:00 PM. "
                + "Current time: " + currentTime
            );
        }
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}