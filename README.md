# Playwright + Selenium Test Automation Suite

## Overview
This repository provides a hybrid test automation framework using Playwright (TypeScript) for UI and API testing, and Selenium (Java) for cross-browser UI testing. It targets demo applications such as Parabank and RESTful Booker.

**Key Capabilities:**
- End-to-end scenarios: Chain API and UI tests (e.g., create via API, verify via UI)
- API contract testing: Validate API schema and responses
- Advanced BDD: Custom steps for API/UI integration and contract checks

## Structure
- `playwright-tests/` — Playwright tests (UI, API), config, and test plan
- `selenium-tests/` — Selenium Java tests and resources
- `shared-config/` — Shared configuration (browser matrix, environment)
- `shared-reporting/` — Allure and reporting integration
- `shared-test-data/` — Sample data for tests
- `ci/` — CI/CD pipeline examples (GitHub Actions, Jenkins)

## Features
- Cross-browser and device coverage (see `shared-config/browser-matrix.json`)
- Allure reporting integration (see `shared-reporting/allure-integration.md`)
- Modular test plans and scenarios for Parabank and RESTful Booker
- Example CI/CD pipelines

## Getting Started
### Playwright
1. `cd playwright-tests`
2. `npm install`
3. `npx playwright test`
4. View reports: `npx playwright show-report ../shared-reporting/playwright-report`

### Selenium
1. `cd selenium-tests`
2. Build and run with Maven or your IDE

## License
See [LICENSE](LICENSE) for details.

## Contributing
Pull requests and issues are welcome!
## Test Data Management

Test data for both Selenium and Playwright tests is managed in the `shared-test-data/` directory. Data is stored in environment-agnostic JSON files (e.g., `parabank-login.json`, `restful-booker.json`) and loaded by tests at runtime. This approach ensures:

- **Reusability:** Data can be shared across frameworks and scenarios.
- **Maintainability:** Centralized updates for all test data.
- **Environment Agnostic:** No hardcoded values in test code; data can be swapped for different environments.

**Best Practices:**
- Use descriptive keys and group related data in each JSON file.
- Reference test data in your test code (see Playwright and Selenium examples).
- For sensitive or environment-specific data, use environment variables or config files in `shared-config/`.

**Example Usage:**
- Playwright: `import loginData from '../../shared-test-data/parabank-login.json';`
- Selenium: Load JSON using a Java library (e.g., Jackson, Gson) or use parameterization.

## Architecture Diagram

```mermaid
graph TD;
    A[Feature Files (Gherkin/BDD)] --> B[Cucumber/Playwright/TestNG Runner];
    B --> C[Step Definitions / Test Specs];
    C --> D[Page Objects / API Utils];
    D --> E[Selenium WebDriver / Playwright];
    D --> F[REST API];
    B --> G[Hooks / Fixtures];
    B --> H[Allure Reporting];
    B --> I[Shared Test Data];
    I --> D;
```

## Contribution Guidelines

- Fork the repository and create a feature branch.
- Follow the existing code style and add comments to new code.
- Add or update tests for any new features or bug fixes.
- Run all tests locally before submitting a pull request.
- Document new features or changes in the README if needed.
- Submit a pull request with a clear description of your changes.

## Troubleshooting

- **WebDriver/Playwright errors:** Ensure correct browser drivers and dependencies are installed.
- **Timeouts or flakiness:** Use smart waits (`WaitUtils.java`, `wait.ts`) and check for network issues.
- **Allure report not generated:** Verify Allure plugin/config and result paths.
- **Test data issues:** Ensure test data files are present and paths are correct.
- **Dependency issues:** Run `npm install` or `mvn clean install` as appropriate.

## Code Comments and Usage Examples

- All step definitions, hooks, and test specs include comments for clarity and maintainability.
- Example Playwright test (see `playwright/integration/api-contract.spec.ts`):
  ```ts
  // API contract test with response time assertion
  test('should return valid contract and respond quickly', async ({ request }) => {
    const start = Date.now();
    const response = await request.get('/api/booking/1');
    expect(response.status()).toBe(200);
    expect(Date.now() - start).toBeLessThan(1500); // <1.5s
    // ...contract assertions...
  });
  ```
- Example Selenium step (see `selenium-tests/src/steps/ParabankLoginSteps.java`):
  ```java
  // Parameterized, reusable login step
  @When("I login with username {string} and password {string}")
  public void i_login_with_username_and_password(String username, String password) {
      loginPage.enterUsername(username);
      loginPage.enterPassword(password);
      loginPage.clickLogin();
  }
  ```
- Example hook (see `selenium-tests/src/steps/Hooks.java`):
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
- See `playwright/integration/` and `selenium-tests/src/steps/` for more examples.

# Selenium Tests Project

## Overview

This project provides automated UI and accessibility testing using Selenium, TestNG, Cucumber, Allure, axe-core, and Lighthouse.  
It supports multiple environments, static code analysis, and can be run locally, in Docker, or via CI/CD (GitHub Actions, Jenkins).

---

## Prerequisites

- Java 11+
- Maven 3.6+
- Chrome browser (for local runs)
- Docker (optional, for containerized runs)

---

## Setup

1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd PlaywrightPlusSelenium
   ```

2. **Install dependencies:**
   Maven will handle dependencies automatically.

---

## Running Tests

### Local

- **Default (dev profile):**
  ```sh
  mvn -f selenium-tests/pom.xml clean verify -Pdev
  ```

- **Other environments:**
  ```sh
  mvn -f selenium-tests/pom.xml clean verify -Pqa
  mvn -f selenium-tests/pom.xml clean verify -Pprod
  ```

- **Override base URL:**
  ```sh
  mvn -f selenium-tests/pom.xml clean verify -Dbase.url=https://custom.example.com
  ```

### Docker

- **Build the image:**
  ```sh
  docker build -t selenium-tests .
  ```

- **Run tests in a container:**
  ```sh
  docker run --rm selenium-tests
  ```

- **Override profile or base URL:**
  ```sh
  docker run --rm -e BASE_URL=https://custom.example.com selenium-tests mvn -f selenium-tests/pom.xml clean verify -Pqa
  ```

---

## Reports

- **Allure:**  
  After running tests, open `selenium-tests/target/allure-report/index.html` (generate with `mvn allure:report` if needed).
- **Surefire:**  
  JUnit XML and HTML reports in `selenium-tests/target/surefire-reports/`.
- **Lighthouse:**  
  Accessibility report in `selenium-tests/target/lighthouse-report.html`.

---

## Static Code Analysis

- **Checkstyle:**  
  ```sh
  mvn -f selenium-tests/pom.xml checkstyle:check
  ```
- **SpotBugs:**  
  ```sh
  mvn -f selenium-tests/pom.xml spotbugs:check
  ```

---

## CI/CD

- **GitHub Actions:**  
  See `.github/workflows/ci.yml` for workflow details.
- **Jenkins:**  
  See `Jenkinsfile` for pipeline configuration.

---

## Customization

- **TestNG suite:**  
  Edit `selenium-tests/testng.xml`.
- **Profiles:**  
  Edit `<profiles>` in `selenium-tests/pom.xml` for environment URLs.

---

## Troubleshooting

- Ensure Chrome is installed for local runs.
- Use Docker for a consistent environment if local issues occur.
- For Allure reports, install Allure CLI if you want to serve reports locally.

---

## License

MIT or your chosen license.
