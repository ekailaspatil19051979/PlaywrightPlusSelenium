# Project File and Folder Structure Explanation

This document explains the purpose of each major file and folder in the project.

---

## Root Directory

- **Dockerfile**: Defines the environment for running tests in a container (dependencies, browsers, etc.).
- **Jenkinsfile**: Script for Jenkins CI/CD pipeline to automate test execution and reporting.
- **LICENSE**: The license under which the project is distributed.
- **package.json**: Node.js project file for Playwright tests: manages dependencies, scripts, and metadata.
- **playwright-test-planner.agent.md**: Documentation or planning notes for Playwright test strategy.
- **playwright.config.ts**: Playwright configuration: test settings, browser options, timeouts, etc.
- **README.md**: Project overview, setup instructions, and usage documentation.
- **run-all-tests-local.bat / run-all-tests-local.sh**: Scripts to run all tests locally on Windows (.bat) or Linux/Mac (.sh).
- **testng.xml**: TestNG configuration for running Selenium Java tests (test suites, groups, etc.).

---

## bdd/
- **features/e2e.feature**: Gherkin feature file for BDD (Behavior-Driven Development) scenarios.
- **steps/e2e.steps.ts**: Step definitions for Playwright BDD tests, mapping Gherkin steps to code.

---

## ci/
- **github-actions-hybrid.yml**: GitHub Actions workflow for CI/CD automation.
- **Jenkinsfile**: (Duplicate for CI folder) Jenkins pipeline script for this submodule.
- **README.md**: CI/CD setup and usage documentation.

---

## playwright/
- **integration/**: Contains Playwright test scripts:
  - **a11y-login.spec.ts**: Accessibility login tests.
  - **api-contract.spec.ts**: API contract validation.
  - **api-ui-integration.spec.ts**: API and UI integration tests.
  - **e2e-api-ui.spec.ts**: End-to-end API and UI tests.
  - **perf-trace-login.spec.ts**: Performance tracing for login.
- **utils/**: Utility functions for Playwright tests:
  - **api.ts**: API helper functions.
  - **login.ts**: Login helper functions.
  - **navigation.ts**: Navigation helpers.
  - **wait.ts**: Custom wait utilities.

---

## playwright-tests/
- **package.json**: Node.js config for Playwright test subproject.
- **playwright.config.ts**: Playwright config for this subproject.
- **test-plan.parabank-restful-booker.md**: Test plan documentation for Parabank and Restful Booker.
- **allure-results/**: Allure test result files for reporting.
- **pages/**, **resources/**, **results/**, **src/**, **tests/**: Supporting files, test data, results, source code, and test scripts for Playwright.

---

## selenium-tests/
- **pom.xml**: Maven project file for Java Selenium tests (dependencies, build config).
- **README.md**: Documentation for Selenium test setup and usage.
- **testng.xml**: TestNG suite configuration for Selenium tests.
- **USAGE-BDD-EXAMPLES.md**: Examples and documentation for BDD usage in Selenium.
- **allure-results/**: Allure test result files for Selenium.
- **features/**, **pages/**, **resources/**, **src/**, **steps/**, **target/**, **tests/**:
  - **features/**: Gherkin feature files for BDD.
  - **pages/**: Page Object Model classes.
  - **resources/**: Test resources (data, configs).
  - **src/**: Java source code (test logic, utilities).
  - **steps/**: Step definitions for BDD.
  - **target/**: Maven build output.
  - **tests/**: Test classes.

---

## shared-config/
- **browser-matrix.json**: Defines browser/OS combinations for cross-browser testing.
- **env.qa.yaml**: Environment-specific configuration (e.g., QA environment variables).

---

## shared-reporting/
- **allure-integration.md**: Documentation for integrating Allure reporting.
- **README.md**: Reporting setup and usage documentation.
- **playwright-report/**: Playwright HTML report output.

---

## shared-test-data/
- **parabank-login.json**: Test data for Parabank login.
- **restful-booker.json**: Test data for Restful Booker.
- **sample-api-data.json**: Sample API test data.

---

## src/
- **main/**: Main application code (if any, not just tests).
- **test/**: Test code (Java, utilities, etc.).

---

## test-results/
- Stores test result output files.

---

If you want a detailed explanation for any specific file or folder, see the main README or ask for more details.

---

# Project Test Case Flows

This section describes typical test case flows implemented in this project for both Playwright and Selenium suites.

## Playwright Test Flows

- **Login Flow:**
  1. Navigate to the login page.
  2. Enter username and password (from test data).
  3. Submit the login form.
  4. Verify successful login (e.g., dashboard or welcome message).

- **API Contract Flow:**
  1. Send API requests to endpoints (e.g., booking, login).
  2. Validate response status, schema, and key fields.

- **UI & API Integration Flow:**
  1. Perform UI actions that trigger backend API calls.
  2. Validate both UI changes and API responses.

- **Accessibility Flow:**
  1. Run accessibility checks (e.g., Axe) on key pages.
  2. Report any violations.

- **Performance Flow:**
  1. Trace page load or login performance.
  2. Collect and analyze performance metrics.

## Selenium Test Flows

- **Login Flow:**
  1. Open the application in a browser.
  2. Enter credentials (from test data).
  3. Submit the login form.
  4. Assert login success (e.g., presence of logout link).

- **Funds Transfer Flow:**
  1. Log in as a user.
  2. Navigate to the "Transfer Funds" page.
  3. Enter transfer details (amount, from/to account).
  4. Submit the transfer.
  5. Assert confirmation or error message.

- **Negative/Edge Case Flow:**
  1. Attempt actions with invalid data (e.g., transfer exceeding balance).
  2. Assert correct error handling and messages.

- **Pop-up/Alert Handling:**
  1. Detect and close any modal or alert before proceeding with test steps.

- **BDD Flows:**
  1. Use Gherkin feature files to describe user scenarios.
  2. Map steps to Java/TypeScript code for execution.

---

These flows ensure robust coverage of authentication, business logic, error handling, and non-functional requirements (accessibility, performance) across both modern and legacy stacks.

---

# Project Architecture Flow

This project uses a hybrid architecture to support both modern (Playwright/Node.js) and legacy (Selenium/Java) test automation, with shared configuration, data, and reporting layers. Here’s a high-level flow:

## 1. Test Authoring
- **Playwright:** Tests written in TypeScript/JavaScript for UI, API, accessibility, and performance.
- **Selenium:** Tests written in Java using TestNG, Cucumber, and Page Object Model.

## 2. Utilities & Shared Resources
- **Utils:** Common helper functions (login, navigation, waits) for both stacks.
- **Test Data:** Centralized JSON/YAML files for credentials, API payloads, etc.
- **Config:** Environment and browser matrix files for cross-browser and environment-specific runs.

## 3. Test Execution
- **Local:** Run via scripts (.bat/.sh) or IDE for rapid feedback.
- **CI/CD:** Jenkinsfile and GitHub Actions automate test runs on code changes, using Docker for consistency.

## 4. Reporting
- **Allure:** Unified reporting for both Playwright and Selenium results.
- **HTML Reports:** Playwright and Selenium generate detailed HTML reports for UI review.

## 5. Results & Feedback
- **Artifacts:** Test results, logs, and screenshots are stored for analysis.
- **Notifications:** CI/CD can be configured to notify teams of failures or regressions.

## 6. Maintenance
- **Modular Structure:** Separate folders for Playwright, Selenium, shared config, and test data for easy updates and scaling.

---

**Architecture Diagram (Textual):**

```
                +-------------------+
                |   Test Authoring  |
                |-------------------|
                | Playwright (TS/JS)|
                | Selenium (Java)   |
                +---------+---------+
                          |
                          v
                +-------------------+
                |  Shared Utilities |
                |  & Test Data      |
                +---------+---------+
                          |
                          v
                +-------------------+
                |   Test Execution  |
                | (Local & CI/CD)   |
                +---------+---------+
                          |
                          v
                +-------------------+
                |    Reporting      |
                | (Allure, HTML)    |
                +---------+---------+
                          |
                          v
                +-------------------+
                |   Results &       |
                |   Feedback        |
                +-------------------+
```

This architecture enables robust, scalable, and maintainable test automation across multiple technologies and environments.
