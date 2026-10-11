# Amazon Selenium Automation - Internship Tasks
# Amazon Selenium Automation Testing Framework

## Project Overview
### ElevanceSkills Internship Project

This project is an extension of the Selenium/TestNG training project and contains automation implementations for six internship tasks using Java, Selenium WebDriver, TestNG and Maven.
This project is based on the Selenium automation training framework by Sonal Garg. The original training project has been extended with six additional Amazon automation tasks as part of the internship.
**Developed by:** Harshini Nagasubramaniyan  
**Project Type:** Selenium Automation Testing  
**Programming Language:** Java  
**Testing Framework:** TestNG  
**Build Tool:** Maven  
**Automation Tool:** Selenium WebDriver  

The project follows a Page Object Model based automation framework.
The project uses Java, Selenium WebDriver, TestNG, and Maven, following a Page Object Model (POM) structure.
---

## Mentor's Training Framework
## 1. Project Overview

**Mentor:** Sonal Garg  
**Original Training Repository:**  
https://github.com/xtechon-techology/selenium_training_by_sonalgarg
This project implements an Amazon website automation testing framework using Java, Selenium WebDriver, TestNG, and Maven.

### Training Topics
The project follows the Page Object Model (POM) design pattern to organize web page elements, automation methods, and test cases. It is based on a mentor-provided Selenium framework and extended with six internship tasks covering product details, product filtering, checkout workflow, login and profile validation, shopping cart operations, and price-drop notifications.

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
The objective is to understand web automation, implement reusable test components, validate website behavior, and organize test execution using TestNG.

## Internship Tasks
## 2. Project Objectives

### Task 1 – Product Details Verification
- Search for a product on Amazon.
- Select a qualifying product from search results.
- Verify the product name and price on its details page.
- Testing window: 3:00 PM–6:00 PM.
- Automate selected Amazon website workflows using Selenium WebDriver.
- Implement test cases using Java and TestNG.
- Follow the Page Object Model design pattern.
- Locate and interact with web elements.
- Validate product information and filtering conditions.
- Automate shopping cart operations.
- Test login and profile-related conditions.
- Implement a price-monitoring and notification workflow.
- Organize dependencies and project execution using Maven.

### Task 2 – Product Filtering
- Search for a product.
- Apply brand, price, and customer-rating filters.
- Validate the filtered product information.
- Testing window: 3:00 PM–6:00 PM.
## 3. Technologies Used

### Task 3 – Product Order Flow
- Search for a product.
- Add the product to the cart.
- Proceed through the checkout flow.
- Verify the checkout/payment stage.
- Testing window: 6:00 PM–7:00 PM.
| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium WebDriver | Browser automation |
| TestNG | Test execution and assertions |
| Maven | Dependency and build management |
| ChromeDriver | Chrome browser automation |
| Eclipse IDE | Development environment |
| Git and GitHub | Version control and project hosting |
| Amazon website | Website under test |

## 4. Project Architecture

The project is organized into the following main components:

```text
Amazon-Selenium-Internship-Tasks/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/ama/qa/
│   │           ├── base/
│   │           ├── config/
│   │           ├── pages/
│   │           └── util/
│   │
│   └── test/
│       └── java/
│           └── testcases/
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
│
├── .mvn/
├── .settings/
├── pom.xml
├── testng.xml
├── README.md
├── .gitignore
├── .gitattributes
├── .classpath
└── .project