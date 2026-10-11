package testcases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;
import com.ama.qa.pages.AmazonLoginPage;

public class AmazonLoginTest extends TestBase {

    AmazonLoginPage amazonLoginPage;

    @BeforeMethod
    public void setup() {
        initialization();
        amazonLoginPage = new AmazonLoginPage();
    }

    @Test(priority = 4)
    public void amazonLogin() {
        amazonLoginPage.enterEmailOrPhone(
            prop.getProperty("USERNAME")
        );
        amazonLoginPage.clickContinue();
        amazonLoginPage.enterPassword(
            prop.getProperty("PASSWORD")
        );
        amazonLoginPage.clickSignIn();
    }
}