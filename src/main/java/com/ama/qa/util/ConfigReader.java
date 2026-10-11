package com.ama.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Class reads base settings from the config.properties file
 */
public class ConfigReader {

    private String username = "";

    private String password = "";

    private String browser = "";

    /**
     * Class constructor loads settings from the file and saves to fields
     */
    public ConfigReader() {

        FileInputStream fis;

        try {

            fis = new FileInputStream(
                    System.getProperty("user.dir")
                    + "/src/main/java/com/ama/qa/config/config.properties");

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            System.out.println("Cant't read config.properties file!");

            return;
        }

        Properties p = new Properties();

        try {

            p.load(fis);

        } catch (IOException e) {

            e.printStackTrace();

            System.out.println("Cant't read config.properties file!");

            return;
        }

        username = p.getProperty("USERNAME");

        password = p.getProperty("PASSWORD");

        browser = p.getProperty("BROWSER");

    }

    /**
     * Method to get the user name
     *
     * @return login
     */
    public String getUsername() {

        return username;

    }

    /**
     * Method to get the password
     *
     * @return password
     */
    public String getPassword() {

        return password;

    }

    public String getBrowser() {

        return browser;

    }

}