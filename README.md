# TravelEase QA Automation

Automation testing project for the **TravelEase Tour Booking Web Application**, built as a QA portfolio project to demonstrate practical web testing skills using **Java, Selenium WebDriver, TestNG, Maven, Page Object Model (POM), and Allure Report**.

The automation suite covers major user and admin workflows and separates stable regression tests from tests that reproduce known application defects.

## Tech Stack

- Java 21
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)
- Allure Report
- IntelliJ IDEA
- Git & GitHub

## Test Coverage

The automation suite currently covers:

- Tour Search
- Admin Authentication
- Cart Management
- Role-based Access Control (RBAC)

Test scenarios include both positive and negative cases.

## Test Suites

### Regression Suite

The regression suite contains stable automated test cases used to verify core functionality.

```bash
mvn clean test
```

Current result:

```text
Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

### Known Bug Suite

Known application defects are separated from the regression suite so they do not affect the stable regression result.

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng-known-bugs.xml
```

Current known defects reproduced by automation:

- **BUG-001 – Search Suggestion:** Selecting a tour from search suggestions does not display the expected tour in the search results.
- **BUG-002 – Authorization:** A restricted admin account can access Category Management directly by URL despite not having the required permission.

These tests are intentionally expected to fail until the corresponding application defects are fixed.

## Project Structure

```text
TravelEase-QA
│
├── src
│   ├── main
│   │   └── java
│   │       └── com.travelease.pages
│   │           ├── HomePage.java
│   │           ├── LoginPage.java
│   │           └── CartPage.java
│   │
│   └── test
│       ├── java
│       │   └── com.travelease
│       │       ├── base
│       │       │   └── BaseTest.java
│       │       ├── listeners
│       │       │   └── TestListener.java
│       │       └── tests
│       │           ├── SearchTourTest.java
│       │           ├── LoginTest.java
│       │           └── CartTest.java
│       │
│       └── resources
│           └── allure.properties
│
├── pom.xml
├── testng.xml
└── testng-known-bugs.xml
```

## Test Design

The project follows the **Page Object Model (POM)** design pattern to separate page interactions from test logic.

Main components:

- **Page Objects** – Store locators and reusable page actions.
- **Test Classes** – Implement automated test scenarios and assertions.
- **BaseTest** – Handles WebDriver setup and teardown.
- **TestListener** – Captures screenshots automatically when a test fails.
- **TestNG Suites** – Separate regression tests and known application defects.

## Failure Evidence

When an automated test fails, the framework automatically:

1. Captures a browser screenshot.
2. Saves the screenshot locally.
3. Attaches the screenshot to the Allure test result.

This makes failed test cases easier to investigate and provides evidence for defect reporting.

## Allure Report

Run the tests first:

```bash
mvn clean test
```

Then generate and open the Allure report:

```bash
mvn allure:serve
```

The report provides test execution status, failure details, stack traces, and screenshot attachments for failed tests.

## Test Environment

The automated tests currently run against the TravelEase application in a local test environment:

```text
http://localhost:3000
```

The TravelEase application must be running before executing the Selenium test suite.

## Test Evidence

### Regression Suite

The regression suite currently contains **8 automated test cases with a 100% pass rate**.

![Regression Allure Report](docs/allure-regression-report.png)

### Known Bug Evidence

The known-bug suite is maintained separately to reproduce confirmed application defects without affecting the stable regression suite.

#### BUG-001 – Search Suggestion

Selecting a tour from the search suggestions does not display the expected tour in the search results.

![BUG-001 Search Suggestion](docs/bug-001-search-suggestion.png)

#### BUG-002 – Authorization / RBAC

A restricted admin account can access Category Management directly through the URL despite not having the required permission.

![BUG-002 RBAC Access](docs/bug-002-rbac-access.png)

## Related Testing Activities

This automation project is part of a broader QA testing workflow for TravelEase that includes:

- Test Scenario Design
- Manual Test Cases
- Test Execution
- Bug Reporting
- Smoke Testing
- API Testing with Postman
- Web UI Automation
- Regression Testing
- Defect Reproduction

## Project Purpose

This project was created to practice and demonstrate an end-to-end QA workflow on a real web application, from manual and API testing to automated regression testing and defect reproduction.

The TravelEase web application and this QA automation framework are maintained as separate projects so that the application source code and testing artifacts can be reviewed independently.