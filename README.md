# Test Automation VAT Project

This project is a test automation framework designed to test a VAT Calculator web application. It utilizes Selenium WebDriver with Java and TestNG for test execution. The framework provides automated tests to calculate Gross Amount and Net Amount based on the given VAT rate.

## Project Structure

The project follows the Page Object Model (POM) design pattern, which promotes code reusability and maintainability. The key components of the project are:

- **src/test/java**: Contains the test classes that define the automated test cases.
- **src/test/java/pages**: Contains the page object classes representing web pages.
- **src/main/java/utilities**: Contains utility classes and helper methods for test setup and data manipulation.
- **pom.xml**: The Maven Project Object Model file that lists the project dependencies and configurations.

## Dependencies

The project relies on the following dependencies, which are managed by Maven:

- **Selenium API**: Provides the core functionality to interact with web browsers.
- **Selenium Chrome Driver**: Enables automated testing with Google Chrome browser.
- **Selenium Java**: The Java bindings for Selenium WebDriver.
- **Selenium Firefox Driver**: Enables automated testing with Mozilla Firefox browser.
- **TestNG**: A testing framework that facilitates organizing and executing test cases.

## How to Run Tests

Before running the tests, make sure you have the following prerequisites set up:

1. JDK 17 or higher is installed.
2. Chrome and/or Firefox browsers are installed.

To execute the automated tests, follow these steps:

1. Clone the project repository to your local machine.
2. Navigate to the project directory.

## Configuring WebDriver Path
   If your WebDriver executables are not in the system's PATH, you can set the WEBDRIVER_PATH environment variable to the directory containing the WebDriver executables. For example:

on windows machine run the following command in terminal:
`setx WEBDRIVER_PATH=C:\path\to\webdriver\directory`
### Running Tests with Maven

You can run the tests using Maven by executing the following command:
`mvn test`
