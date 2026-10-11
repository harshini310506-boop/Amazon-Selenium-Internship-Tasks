package testcases;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ama.qa.base.TestBase;

public class Task4LoginProfileTest extends TestBase {

    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {

        /*
         * TASK 4 TIME RESTRICTION
         *
         * Testing is allowed only between
         * 12:00 PM and 3:00 PM.
         */

        LocalTime currentTime = LocalTime.now();

        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(15, 0);

        if (currentTime.isBefore(startTime)
                || currentTime.isAfter(endTime)) {

            throw new SkipException(
                    "Task 4 can run only between "
                    + "12:00 PM and 3:00 PM. "
                    + "Current time: "
                    + currentTime
            );
        }

        initialization();

        wait = new WebDriverWait(
                getdriver(),
                Duration.ofSeconds(30)
        );
    }

    @Test
    public void loginAndValidateProfileName() {

        System.out.println("========================================");
        System.out.println("TASK 4 - LOGIN AND PROFILE VALIDATION");
        System.out.println("========================================");

        /*
         * Read credentials from config.properties.
         */

        String username = prop.getProperty("USERNAME");
        String password = prop.getProperty("PASSWORD");

        Assert.assertNotNull(
                username,
                "USERNAME is missing in config.properties."
        );

        Assert.assertNotNull(
                password,
                "PASSWORD is missing in config.properties."
        );

        Assert.assertFalse(
                username.trim().isEmpty(),
                "USERNAME is empty."
        );

        Assert.assertFalse(
                password.trim().isEmpty(),
                "PASSWORD is empty."
        );

        /*
         * STEP 1
         * Open Amazon.
         */

        getdriver().get("https://www.amazon.in/");

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.tagName("body")
                )
        );

        System.out.println("Amazon home page opened.");

        /*
         * STEP 2
         * Click Account & Lists / Sign in.
         */

        WebElement accountLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("nav-link-accountList")
                )
        );

        accountLink.click();

        System.out.println(
                "Amazon Account & Lists / Sign in clicked."
        );

        /*
         * STEP 3
         * Enter registered phone number.
         */

        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(
                                "//input[@id='ap_email' "
                                + "or @name='email']"
                        )
                )
        );

        usernameField.clear();
        usernameField.sendKeys(username);

        System.out.println("Username entered.");

        /*
         * STEP 4
         * Continue.
         */

        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("continue")
                )
        );

        continueButton.click();

        System.out.println("Continue button clicked.");

        /*
         * STEP 5
         *
         * Amazon may show:
         *
         * - Password page
         * OR
         * - Passkey / OTP authentication page
         *
         * We handle the actual Amazon authentication
         * flow without bypassing it.
         */

        WebDriverWait authenticationWait =
                new WebDriverWait(
                        getdriver(),
                        Duration.ofSeconds(30)
                );

        boolean passwordPage = false;

        try {

            authenticationWait.until(driver -> {

                List<By> passwordLocators =
                        Arrays.asList(
                                By.id("ap_password"),
                                By.name("password"),
                                By.xpath("//input[@type='password']")
                        );

                for (By locator : passwordLocators) {

                    List<WebElement> elements =
                            driver.findElements(locator);

                    for (WebElement element : elements) {

                        if (element.isDisplayed()) {
                            return true;
                        }
                    }
                }

                return false;
            });

            passwordPage = true;

        } catch (TimeoutException ignored) {
            passwordPage = false;
        }

        /*
         * NORMAL PASSWORD LOGIN
         */

        if (passwordPage) {

            System.out.println(
                    "Amazon password authentication detected."
            );

            WebElement passwordField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[@id='ap_password' "
                                    + "or @name='password']"
                            )
                    )
            );

            passwordField.clear();
            passwordField.sendKeys(password);

            System.out.println("Password entered.");

            WebElement signInButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[@id='signInSubmit']"
                                    + " | //button[@type='submit']"
                            )
                    )
            );

            signInButton.click();

            System.out.println(
                    "Sign in button clicked."
            );

        } else {

            /*
             * AMAZON OTP / PASSKEY FLOW
             *
             * Current Amazon account may show:
             *
             * Use your passkey
             * Send OTP to +91 ***-**-39
             * Sign in another way
             */

            String pageText =
                    getdriver()
                            .findElement(By.tagName("body"))
                            .getText();

            System.out.println("----------------------------------------");
            System.out.println(
                    "Amazon authentication page detected."
            );
            System.out.println("----------------------------------------");

            System.out.println(pageText);

            /*
             * Click Send OTP option.
             */

            WebElement sendOtpOption = null;

            List<By> otpLocators =
                    Arrays.asList(

                            By.xpath(
                                    "//*[contains("
                                    + "translate(normalize-space(.),"
                                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                    + "'abcdefghijklmnopqrstuvwxyz'),"
                                    + "'send otp')]"
                            ),

                            By.xpath(
                                    "//a[contains(translate(.,"
                                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                    + "'abcdefghijklmnopqrstuvwxyz'),"
                                    + "'send otp')]"
                            ),

                            By.xpath(
                                    "//button[contains(translate(.,"
                                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                    + "'abcdefghijklmnopqrstuvwxyz'),"
                                    + "'send otp')]"
                            ),

                            By.xpath(
                                    "//*[contains(text(),"
                                    + "'Send OTP')]"
                            )
                    );

            for (By locator : otpLocators) {

                try {

                    List<WebElement> elements =
                            getdriver().findElements(locator);

                    for (WebElement element : elements) {

                        if (element.isDisplayed()
                                && element.isEnabled()) {

                            sendOtpOption = element;
                            break;
                        }
                    }

                    if (sendOtpOption != null) {
                        break;
                    }

                } catch (Exception ignored) {
                }
            }

            Assert.assertNotNull(
                    sendOtpOption,
                    "Amazon OTP option was not found."
            );

            sendOtpOption.click();

            System.out.println(
                    "Send OTP option selected."
            );

            /*
             * Wait for OTP input field.
             */

            WebElement otpField =
                    authenticationWait.until(driver -> {

                        List<By> otpFieldLocators =
                                Arrays.asList(

                                        By.xpath(
                                                "//input[contains("
                                                + "translate(@id,"
                                                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                                + "'abcdefghijklmnopqrstuvwxyz'),"
                                                + "'otp')]"
                                        ),

                                        By.xpath(
                                                "//input[contains("
                                                + "translate(@name,"
                                                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                                + "'abcdefghijklmnopqrstuvwxyz'),"
                                                + "'otp')]"
                                        ),

                                        By.xpath(
                                                "//input[@type='tel']"
                                        ),

                                        By.xpath(
                                                "//input[@inputmode='numeric']"
                                        )
                                );

                        for (By locator :
                                otpFieldLocators) {

                            List<WebElement> elements =
                                    driver.findElements(locator);

                            for (WebElement element :
                                    elements) {

                                if (element.isDisplayed()
                                        && element.isEnabled()) {

                                    return element;
                                }
                            }
                        }

                        return null;
                    });

            Assert.assertNotNull(
                    otpField,
                    "Amazon OTP input field was not displayed."
            );

            System.out.println("----------------------------------------");
            System.out.println(
                    "OTP INPUT FIELD IS READY."
            );
            System.out.println(
                    "Enter the OTP sent by Amazon manually"
            );
            System.out.println(
                    "in the Amazon browser window."
            );
            System.out.println("----------------------------------------");

            /*
             * DO NOT read, generate, or bypass the OTP.
             *
             * User enters the genuine Amazon OTP
             * directly into the browser.
             *
             * Now wait for Amazon to complete login.
             */

            WebDriverWait loginWait =
                    new WebDriverWait(
                            getdriver(),
                            Duration.ofSeconds(120)
                    );

            loginWait.until(driver -> {

                try {

                    String currentUrl =
                            driver.getCurrentUrl();

                    String bodyText =
                            driver.findElement(
                                    By.tagName("body")
                            ).getText();

                    /*
                     * Successful Amazon login normally
                     * returns to Amazon navigation.
                     */

                    boolean accountNavigation =
                            !driver.findElements(
                                    By.id(
                                            "nav-link-accountList"
                                    )
                            ).isEmpty();

                    boolean amazonHome =
                            currentUrl.contains(
                                    "amazon.in"
                            )
                            && !currentUrl.contains(
                                    "/ap/signin"
                            )
                            && !currentUrl.contains(
                                    "/ax/claim/auth"
                            );

                    boolean profileVisible =
                            bodyText.contains("Account & Lists")
                            || bodyText.contains("Hello");

                    return accountNavigation
                            && amazonHome
                            && profileVisible;

                } catch (Exception e) {

                    return false;
                }
            });

            System.out.println(
                    "Amazon authentication completed."
            );
        }

        /*
         * STEP 6
         * Confirm successful login.
         */

        WebElement loggedInAccountLink =
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                By.id("nav-link-accountList")
                        )
                );

        Assert.assertNotNull(
                loggedInAccountLink,
                "Amazon account navigation was not found after login."
        );

        System.out.println(
                "Login completed successfully."
        );

        /*
         * STEP 7
         * Read actual profile information.
         */

        String accountText =
                loggedInAccountLink.getText().trim();

        System.out.println(
                "Account Information:"
        );

        System.out.println(accountText);

        Assert.assertFalse(
                accountText.isEmpty(),
                "Account/profile information is empty."
        );

        /*
         * STEP 8
         * Extract profile name.
         *
         * Amazon may display:
         *
         * Hello, Ben
         * Account & Lists
         *
         * or:
         *
         * Hello, Ben
         */

        String profileName = "";

        String[] accountLines =
                accountText.split("\\r?\\n");

        for (String line : accountLines) {

            String cleanLine =
                    line.trim();

            if (cleanLine.isEmpty()) {
                continue;
            }

            if (cleanLine.equalsIgnoreCase(
                    "Account & Lists")) {

                continue;
            }

            if (cleanLine.equalsIgnoreCase(
                    "Hello")) {

                continue;
            }

            if (cleanLine.toLowerCase()
                    .startsWith("hello,")) {

                cleanLine =
                        cleanLine
                                .substring(6)
                                .trim();
            }

            if (!cleanLine.isEmpty()) {

                profileName =
                        cleanLine;

                break;
            }
        }

        Assert.assertFalse(
                profileName.isEmpty(),
                "Profile name could not be identified."
        );

        System.out.println(
                "Profile Name: "
                        + profileName
        );

        /*
         * STEP 9
         *
         * TASK REQUIREMENT:
         *
         * Profile name must NOT contain:
         *
         * A
         * C
         * G
         * I
         * L
         * K
         */

        String restrictedCharacters =
                "ACGILK";

        String upperCaseProfileName =
                profileName.toUpperCase();

        Character restrictedCharacterFound =
                null;

        for (char character :
                restrictedCharacters.toCharArray()) {

            if (upperCaseProfileName
                    .indexOf(character) >= 0) {

                restrictedCharacterFound =
                        character;

                break;
            }
        }

        /*
         * Test MUST fail if a restricted
         * character is present.
         */

        Assert.assertNull(
                restrictedCharacterFound,
                "Task 4 condition failed. "
                + "Profile name contains restricted "
                + "character: "
                + restrictedCharacterFound
        );

        System.out.println(
                "Profile Name Validation: PASSED"
        );

        System.out.println(
                "No restricted characters "
                + "(A, C, G, I, L, K) were found."
        );

        System.out.println("========================================");
        System.out.println(
                "TASK 4 PASSED - Login functionality "
                + "and profile details validated."
        );
        System.out.println("========================================");
    }

    @AfterMethod
    public void tearDown() {

        try {

            if (driver != null) {

                driver.quit();
                driver = null;
            }

        } catch (Exception ignored) {
        }
    }
}