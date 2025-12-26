# Selenium BDD Test Suite for Parabank

This module contains robust, maintainable Selenium UI tests for the Parabank demo application, implemented using the Page Object Model (POM) and BDD (Cucumber + TestNG).

## Structure

- **pages/**: Page Object Model classes for each Parabank page/feature
- **steps/**: Cucumber step definitions using page objects
- **features/**: Gherkin feature files describing test scenarios
- **runner/**: TestNG runner classes for each feature

## Key Features
- Page Object Model (POM) for maintainability
- BDD with Cucumber and Gherkin
- Robust error handling for demo site instability
- Headless/headed Chrome execution (set `-Dheadless=true` for headless)
- Modular, reusable step definitions

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

## Adding/Editing Tests
- Add new page objects in `pages/`
- Add or update step definitions in `steps/`
- Add new Gherkin scenarios in `features/`
- Add or update runners in `runner/`

## Troubleshooting
- If the demo site is down or unstable, tests may fail or skip steps. All tests are designed to handle common site errors gracefully.
- For browser/driver issues, ensure Chrome and ChromeDriver are compatible and on your PATH.

## Authors
- Automated by GitHub Copilot

---
For more details, see the root README.md or contact your automation maintainer.
