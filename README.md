# Amazon Selenium Automation - Internship Tasks
# Amazon Selenium Automation – Training and Internship Tasks

## Project Overview

This project is an extension of the Selenium/TestNG training project and contains automation implementations for six internship tasks using Java, Selenium WebDriver, TestNG and Maven.
This project is based on the Selenium automation training framework by Sonal Garg. The original training project has been extended with six additional Amazon automation tasks as part of the internship.

The project follows a Page Object Model based automation framework.
The project uses Java, Selenium WebDriver, TestNG, and Maven, following a Page Object Model (POM) structure.

## Mentor's Training Framework

**Mentor:** Sonal Garg  
**Original Training Repository:**  
https://github.com/xtechon-techology/selenium_training_by_sonalgarg

### Training Topics

1. Amazon Home Page Automation
2. Amazon Login Functionality
3. Amazon Product Search
4. Amazon Product Details Verification
5. Amazon Product Checkout
6. Shopping Cart Functionality
7. Page Object Model (POM)
8. Selenium WebDriver
9. TestNG Test Execution
10. Maven Project and Dependency Management
11. Test Automation Framework Structure
12. Configuration Management Using Properties Files

## Internship Tasks

### Task 1 – Product Details Verification
- Search for a product on Amazon.
- Select a qualifying product from search results.
- Verify the product name and price on its details page.
- Testing window: 3:00 PM–6:00 PM.

### Task 2 – Product Filtering
- Search for a product.
- Apply brand, price, and customer-rating filters.
- Validate the filtered product information.
- Testing window: 3:00 PM–6:00 PM.

### Task 3 – Product Order Flow
- Search for a product.
- Add the product to the cart.
- Proceed through the checkout flow.
- Verify the checkout/payment stage.
- Testing window: 6:00 PM–7:00 PM.

**Note:** The automation does not complete a real payment or place an actual order.

### Task 4 – Login and Profile Validation
- Automate Amazon login.
- Handle the authentication flow when manual OTP verification is required.
- Retrieve and validate the profile name against the task's character restrictions.
- Testing window: 12:00 PM–3:00 PM.

### Task 5 – Multiple Products and Cart Total
- Search for qualifying products.
- Add multiple products to the cart.
- Verify the cart contents and total price.
- Testing window: 6:00 PM–7:00 PM.

### Task 6 – Price Monitoring and Notification
- Retrieve a product's current price.
- Compare the price against a specified threshold.
- Verify the notification mechanism when the price falls below the threshold.

**Note:** The notification logic was verified using a controlled test price. This does not represent a confirmed live Amazon price drop.

## Technologies Used

- Java
- Selenium WebDriver
- TestNG
- Maven
- Eclipse IDE
- Google Chrome

## Project Structure

```text
src/main/java
├── com.ama.qa.base
├── com.ama.qa.config
├── com.ama.qa.pages
└── com.ama.qa.util

src/test/java
└── testcases
    ├── Task1ProductDetailsTest.java
    ├── Task2ProductFilterTest.java
    ├── Task3OrderFlowTest.java
    ├── Task4LoginProfileTest.java
    ├── Task5MultipleProductsCartTest.java
    └── Task6PriceDropNotificationTest.javaAmazon-Selenium-Internship-Tasks/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com.ama.qa/
│   │           ├── base/
│   │           ├── config/
│   │           ├── pages/
│   │           └── util/
│   └── test/
│       └── java/
│           └── testcases/s
│               ├── AmazonHomeTest.java
│               ├── AmazonLoginTest.java
│               ├── AmazonProductCheckoutTest.java
│               ├── AmazonProductDetailsTest.java
│               ├── AmazonSearchProductsTest.java
│               ├── ShoppingCartTest.java
│               ├── Task1ProductDetailsTest.java
│               ├── Task2ProductFilterTest.java
│               ├── Task3OrderFlowTest.java
│               ├── Task4LoginProfileTest.java
│               ├── Task5MultipleProductsCartTest.java
│               └── Task6PriceDropNotificationTest.java
├── pom.xml
├── testng.xml
└── README.md