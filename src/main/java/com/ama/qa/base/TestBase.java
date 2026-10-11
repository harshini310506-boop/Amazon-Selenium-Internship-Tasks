package com.ama.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

    public static WebDriver driver = null;
    public static Properties prop;

    public TestBase() {

        try {
            prop = new Properties();

            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir")
                    + "/src/main/java/com/ama/qa/config/config.properties");
            prop.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialization() {
        String browsername = prop.getProperty("browser");

        if (browsername.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("url"));
    }

    public static final WebDriver getdriver() {

        if (driver == null) {

            driver = new ChromeDriver();

            return driver;

        } else {

            return driver;
        }
    }
}