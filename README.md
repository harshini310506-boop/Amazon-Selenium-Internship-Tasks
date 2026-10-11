# Amazon Selenium Automation Testing Framework

## ElevanceSkills Internship Project

**Developed by:** Harshini Nagasubramaniyan  
**Project:** Amazon Selenium Automation Testing  
**Programming Language:** Java  
**Automation Tool:** Selenium WebDriver  
**Testing Framework:** TestNG  
**Build Tool:** Maven  
**IDE:** Eclipse  

---

## 1. Project Overview

This project focuses on automating selected workflows on the Amazon website using Java, Selenium WebDriver, TestNG, and Maven.

The project uses a mentor-provided Selenium automation framework and includes six additional internship task classes. These tasks cover product details validation, product filtering, order workflow validation, login and profile validation, multiple-product shopping cart operations, and price-drop notifications.

The framework is organized using the Page Object Model (POM) design pattern to separate page-related operations from test cases and improve code organization and reusability.

## 2. Project Objectives

- Automate selected Amazon website workflows.
- Develop test cases using Java and Selenium WebDriver.
- Use TestNG for test execution and assertions.
- Implement the Page Object Model design pattern.
- Validate product details and filtering conditions.
- Automate shopping cart operations.
- Validate login and profile-related conditions.
- Implement price monitoring and notification logic.
- Manage dependencies using Maven.
- Maintain and document source code using Git and GitHub.

## 3. Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Selenium WebDriver | Browser automation |
| TestNG | Test execution and assertions |
| Maven | Dependency management and project build |
| Google Chrome | Browser under test |
| ChromeDriver | Browser automation driver |
| Eclipse IDE | Development environment |
| Git | Version control |
| GitHub | Source code hosting |

## 4. Project Structure

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
```

*Note: The structure above describes the intended project organization. Refer to the actual repository for the complete list of files and directories.*

## 5. Framework Components

### Base Package

Contains the base test configuration and browser initialization used by the automation framework.

### Pages Package

Contains page classes that organize web elements and operations associated with individual website pages.

The framework includes page classes such as:

- `AmazonHomePage`
- `AmazonLoginPage`
- `AmazonProductDetailsPage`
- `ShoppingCartPage`

### Utility Package

Contains reusable utility and configuration-related components.

### Test Cases Package

Contains the mentor framework's original test classes and the six additional internship task classes.

## 6. Internship Tasks

### Task 1: Product Details Validation

**Test Class:** `Task1ProductDetailsTest.java`

**Objective:**
- Search for products on Amazon.
- Identify a product matching the specified selection criteria.
- Open the selected product.
- Retrieve and validate product details, including the title and price.

**Expected Outcome:**

The automation should identify a qualifying product and validate the required product information.

### Task 2: Product Filtering

**Test Class:** `Task2ProductFilterTest.java`

**Objective:**
- Search for products.
- Apply the specified brand and price conditions.
- Validate the applicable rating and filtering criteria.
- Check whether the displayed products satisfy the task requirements.

**Expected Outcome:**

The automation should validate the product filtering conditions defined in the task.

### Task 3: Order Flow Automation

**Test Class:** `Task3OrderFlowTest.java`

**Objective:**
- Search for a product.
- Open the product details page.
- Perform the required shopping cart operation.
- Validate the shopping cart and checkout workflow.

**Expected Outcome:**

The automation should validate the required order-flow stages without completing a real purchase or payment.

### Task 4: Login and Profile Validation

**Test Class:** `Task4LoginProfileTest.java`

**Objective:**
- Navigate through the required login or profile workflow.
- Handle manual authentication steps, including OTP entry when required.
- Validate profile information against the specified conditions.
- Follow the task's execution time requirements.

**Expected Outcome:**

The automation should validate the specified profile conditions when the required workflow can be completed.

**Security:** Login credentials and OTPs should not be committed to the public repository.

### Task 5: Multiple Products and Shopping Cart

**Test Class:** `Task5MultipleProductsCartTest.java`

**Objective:**
- Search for the products specified in the task.
- Select suitable products.
- Add the selected products to the shopping cart.
- Validate cart contents and the displayed total price.

**Expected Outcome:**

The automation should verify the selected cart items and validate the total against the configured task requirements.

### Task 6: Price-Drop Notification

**Test Class:** `Task6PriceDropNotificationTest.java`

**Objective:**
- Monitor the price of a selected product.
- Compare the observed price against a configured threshold.
- Trigger a notification when the configured condition is satisfied.

**Expected Outcome:**

The automation should detect a price below the configured threshold and produce the expected notification.

A controlled test price can be used to validate the notification logic. A controlled test does not establish that a real Amazon price drop occurred.

## 7. Installation and Setup

### Prerequisites

Install or configure the following:

- Java Development Kit (JDK) 17
- Eclipse IDE
- Maven
- Google Chrome
- Git

### Step 1: Clone the Repository

Run the following command in a terminal:

```bash
git clone https://github.com/harshini310506-boop/Amazon-Selenium-Internship-Tasks.git
```

### Step 2: Import the Project into Eclipse

1. Open Eclipse IDE.
2. Select **File → Import**.
3. Select **Maven → Existing Maven Projects**.
4. Browse to the cloned repository folder.
5. Select the project and finish the import.

### Step 3: Update Maven Dependencies

1. Right-click the project in Eclipse.
2. Select **Maven → Update Project**.
3. Wait for Maven to resolve the dependencies.
4. Check the Problems tab for build errors.

### Step 4: Configure Browser Execution

Ensure Google Chrome is installed and the project's WebDriver configuration can start the browser successfully.

### Step 5: Execute the Tests

Open `testng.xml` and run it as a TestNG Suite.

Individual task classes can also be executed separately as TestNG tests.

Some workflows may require manual authentication, OTP entry, an appropriate execution time, or changes to accommodate the website's current behavior.

## 8. Test Execution and Results

The project contains six internship task classes in addition to the mentor framework's original test classes.

Test results should be verified using the actual TestNG execution output.

| Task | Test Class | Result |
|---|---|---|
| Task 1 | `Task1ProductDetailsTest.java` | Verify from TestNG output |
| Task 2 | `Task2ProductFilterTest.java` | Verify from TestNG output |
| Task 3 | `Task3OrderFlowTest.java` | Verify from TestNG output |
| Task 4 | `Task4LoginProfileTest.java` | Verify from TestNG output |
| Task 5 | `Task5MultipleProductsCartTest.java` | Verify from TestNG output |
| Task 6 | `Task6PriceDropNotificationTest.java` | Verify from TestNG output |

Record the actual pass, failure, and skipped counts after running each task.

A test should be reported as successful only when the execution output confirms the expected result.

## 9. Challenges and Learning Outcomes

This project provides practical experience in:

- Java programming for automation testing.
- Selenium WebDriver browser automation.
- TestNG test execution and assertions.
- Page Object Model implementation.
- Locating and interacting with web elements.
- Handling dynamic website content and explicit waits.
- Automating product searches and shopping cart operations.
- Validating product and profile information.
- Implementing threshold-based price monitoring.
- Managing dependencies using Maven.
- Organizing and maintaining code in GitHub.

## 10. Limitations

- Website layouts and element locators may change.
- Product availability, prices, and search results can vary.
- Login and OTP workflows may require manual interaction.
- Network conditions and website behavior can affect test execution.
- Controlled price-drop tests do not prove that a real price drop occurred.
- No real order or payment should be completed during testing.

## 11. Dataset and Documentation

Supporting dataset documentation for the internship tasks is maintained separately in Google Drive.

**Google Drive Dataset Folder:**

https://drive.google.com/drive/folders/1SZuOzlBUWEEhIE5qQlmp-7EiCdcoHrgW?usp=sharing

The folder should contain the actual datasets or task documentation used for this project. Access must be configured so the evaluator can view the files.

## 12. GitHub Repository

**Project Repository:**

https://github.com/harshini310506-boop/Amazon-Selenium-Internship-Tasks

The repository contains the Java source code, Maven configuration, TestNG suite configuration, framework components, and internship task implementations.

## 13. Acknowledgement

I would like to acknowledge the guidance provided through the Selenium automation training framework and the opportunity to practise web automation through the ElevanceSkills internship tasks.

This project helped me develop practical knowledge of Java, Selenium WebDriver, TestNG, Maven, and structured automation testing.

## 14. Author

**Harshini Nagasubramaniyan**

Civil Engineering Student | Selenium Automation Testing Intern

**GitHub Repository:**  
https://github.com/harshini310506-boop/Amazon-Selenium-Internship-Tasks
