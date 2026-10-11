package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonHomePage;
import com.ama.qa.pages.AmazonLoginPage;
import com.ama.qa.pages.AmazonProductDetailsPage;
import com.ama.qa.pages.ShoppingCartPage;

public class AmazonProductDetailsTest extends TestBase {

    AmazonProductDetailsPage amazonProductDetailsPage;
    AmazonHomePage amazonHomePage;
    ShoppingCartPage shoppingCartPage;
    AmazonLoginPage amazonLoginPage;

    @BeforeMethod
    public void setup() {
        initialization();

        amazonProductDetailsPage = new AmazonProductDetailsPage();
        amazonHomePage = new AmazonHomePage();
        shoppingCartPage = new ShoppingCartPage();
        amazonLoginPage = new AmazonLoginPage();
    }

    @Test(priority = 2)
    public void VerifyProductDetailsAndAddToCart() throws InterruptedException {
        String productTitle = "Noise Twist Go Bluetooth Calling Smart Watch for Women with Sleek Metal Dial, Glossy Finish, 1.39\" Display, 100+ Watch Faces, IP68, Sleep Tracking, Voice Assistance, Upto 7 Days Battery (Gold Link)";
        String productPrice = "1,599";

        Assert.assertEquals(
            amazonProductDetailsPage.getSearchedProductTitle(),
            productTitle
        );

        Assert.assertEquals(
            amazonProductDetailsPage.getSearchedProductPrice(),
            productPrice
        );

        amazonProductDetailsPage.clickOnAddToCart();
        amazonProductDetailsPage.clickProceedToCheckOut();
        amazonHomePage.clickOnCart();
    }
}