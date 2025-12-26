# Selenium BDD Test Suite for Parabank

This module contains robust, maintainable Selenium UI tests for the Parabank demo application, implemented using the Page Object Model (POM) and BDD (Cucumber + TestNG).

## Architecture

```mermaid
flowchart TD
    A[Feature Files<br/>(Gherkin)] --> B[Cucumber Runner<br/>(TestNG)]
    B --> C[Step Definitions]
    C --> D[Page Objects]
    C --> E[API Utilities]
    D --> F[Selenium WebDriver]
    E --> G[REST API]
    B --> H[Hooks]
    B --> I[Allure Reporting]
```

- **Feature Files:** Describe scenarios using Gherkin syntax, grouped and filtered by tags.
- **Cucumber Runner:** Executes scenarios, manages hooks, and integrates with TestNG.
- **Step Definitions:** Glue code for mapping Gherkin steps to Java methods.
- **Page Objects:** Encapsulate UI interactions for maintainability.
- **API Utilities:** Support API calls and contract validation.
- **Hooks:** Global setup/teardown for stability and reusability.
- **Allure Reporting:** Aggregates results, screenshots, and logs.

## Architecture Diagram

```mermaid
graph TD;
    A[Feature Files (Gherkin)] --> B[Cucumber Runner (TestNG)];
    B --> C[Step Definitions];
    C --> D[Page Objects];
    C --> E[API Utilities];
    D --> F[Selenium WebDriver];
    E --> G[REST API];
    B --> H[Hooks];
    B --> I[Allure Reporting];
```

## Structure

- **pages/**: Page Object Model classes for each Parabank page/feature
- **steps/**: Cucumber step definitions using page objects
- **features/**: Gherkin feature files describing test scenarios
- **runner/**: TestNG runner classes for each feature
- **api/**: API utility and contract tests
- **integration/**: End-to-end API & UI chained scenarios

## Key Features
- Page Object Model (POM) for maintainability
- BDD with Cucumber and Gherkin
- Robust error handling for demo site instability
- Headless/headed Chrome execution (set `-Dheadless=true` for headless)
- Modular, reusable step definitions
- **API & UI Integration:** Chain API and UI tests for end-to-end validation
- **API Contract Testing:** Validate API schema and responses
- **Advanced BDD:** Custom steps for API/UI chaining and contract checks
- **Scenario Tagging:** Use tags like `@smoke`, `@regression`, `@api`, `@integration` to group and filter scenarios
- **Hooks & Reusability:** Global hooks for setup/teardown and custom step definitions for DRY, stable tests
- **Test Stability:** Built-in waits, retries, and error handling to reduce flakiness

## How to Run

1. **Install dependencies**
   - Ensure you have Java 11+ and Maven installed
   - Chrome browser must be available on your system

2. **Run all tests in headless mode:**
   ```sh
   mvn test -Dheadless=true
   ```
   Or for headed mode:
   ```sh
   mvn test
   ```

3. **Run a specific feature:**
   Edit the corresponding runner in `src/test/java/runner/` to point to the desired feature file.

4. **Run API contract tests:**
   ```sh
   mvn test -Dtest=ApiContractTest
   ```

5. **Run end-to-end API & UI integration tests:**
   ```sh
   mvn test -Dtest=ApiUiIntegrationTest
   ```

6. **Run tests by tag:**
   ```sh
   mvn test -Dcucumber.filter.tags="@smoke"
   ```

## Usage Examples

### Example: Tagging and Running a Smoke Test

```gherkin
# features/login.feature
@smoke
Scenario: Valid login
  Given I am on the login page
  When I login with username "demo" and password "demo"
  Then I should see "Account Overview"
```

```sh
mvn test -Dcucumber.filter.tags="@smoke"
```

### Example: Custom Step Definition

```java
// steps/CommonSteps.java
// ...existing code...
@Given("I am logged in as {string}")
public void i_am_logged_in_as(String user) {
    // Login logic using Page Object
}
```

### Example: Using Hooks

```java
// steps/Hooks.java
@Before
public void setUp(Scenario scenario) {
    // Initialize WebDriver, set timeouts, log scenario info
}
@After
public void tearDown(Scenario scenario) {
    // Quit WebDriver, capture screenshot on failure
}
```

## Best Practices: Scenario Grouping, Hooks, and Stability

- **Use tags** in your feature files to group and filter scenarios:
  - Example: `@smoke`, `@regression`, `@critical`, `@api`, `@ui`
  - Run only smoke tests: `mvn test -Dcucumber.filter.tags="@smoke"`

- **Add hooks** for setup/teardown in your step definitions (see `steps/Hooks.java`):
  - Use `@Before` and `@After` for global or tagged setup/cleanup
  - Example: Take screenshots on failure, reset state, manage test data

- **Create custom, reusable step definitions** for common actions:
  - Parameterize steps for login, registration, navigation, etc.
  - Example: `Given user logs in as "<username>" with password "<password>"`

- **Test Stability & Flakiness Reduction:**
  - Use explicit waits (see `WaitUtils.java`) instead of Thread.sleep
  - Add robust error handling and retry logic (see `RetryAnalyzer.java`)
  - Isolate test data and reset state between tests
  - Use Allure reporting for diagnostics and artifact attachment

## TODO: Accessibility Testing Setup

- Add axe-core dependency to pom.xml:
  ```xml
  <dependency>
    <groupId>com.deque.axe-core</groupId>
    <artifactId>axe-selenium-java</artifactId>
    <version>4.8.2</version>
    <scope>test</scope>
  </dependency>
  ```
- Download axe.min.js from https://cdnjs.cloudflare.com/ajax/libs/axe-core/4.8.2/axe.min.js
- Place axe.min.js in src/test/resources/
- In your test, load axe.min.js as:
  ```java
  private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");
  ```
- Run AccessibilityTest to analyze and report accessibility violations.

## Contribution Guidelines

- Fork the repository and create a feature branch.
- Follow the existing code style and add comments to new code.
- Add or update tests for any new features or bug fixes.
- Run all tests locally before submitting a pull request.
- Document new features or changes in the README if needed.
- Submit a pull request with a clear description of your changes.

## Troubleshooting

- **WebDriver errors:** Ensure the correct browser driver is installed and available in your PATH.
- **Timeouts or flakiness:** Use explicit waits (see `WaitUtils.java`) and check for network issues.
- **Allure report not generated:** Verify the Allure plugin is configured and results are written to the correct directory.
- **Test data issues:** Ensure test data files are present and paths are correct.
- **Dependency issues:** Run `mvn clean install` to resolve and update dependencies.

## Code Comments and Usage Examples

- All step definitions and hooks include comments for clarity and maintainability.
- Example parameterized step (see `ParabankLoginSteps.java`):
  ```java
  // Parameterized, reusable login step
  @When("I login with username {string} and password {string}")
  public void i_login_with_username_and_password(String username, String password) {
      loginPage.enterUsername(username);
      loginPage.enterPassword(password);
      loginPage.clickLogin();
  }
  ```
- Example hook (see `Hooks.java`):
  ```java
  @Before
  public void setUp(Scenario scenario) {
      // Log scenario tags, initialize WebDriver, etc.
  }
  @After
  public void tearDown(Scenario scenario) {
      // Take screenshot on failure, quit WebDriver
  }
  ```
- See feature files for usage of tags and scenario grouping.

## Authors
- Automated by GitHub Copilot

---
For more details, see the root README.md or contact your automation maintainer.
