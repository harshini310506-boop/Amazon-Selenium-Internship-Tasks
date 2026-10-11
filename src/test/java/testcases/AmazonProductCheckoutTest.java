package testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonProductCheckoutPage;

public class AmazonProductCheckoutTest extends TestBase {

    AmazonProductCheckoutPage amazonProductCheckoutPage;

    @BeforeMethod
    public void setup() {
        initialization();
        amazonProductCheckoutPage = new AmazonProductCheckoutPage();
    }

    @Test(priority = 5)
    public void useAddress() {
        amazonProductCheckoutPage.clickShipToAddressButton();
    }
}