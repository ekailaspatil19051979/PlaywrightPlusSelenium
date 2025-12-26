#!/bin/bash
# Unified script to run Selenium and Playwright tests with Allure reporting

set -e

# Run Selenium tests and generate Allure report
cd selenium-tests
mvn clean test -Dheadless=true
mvn allure:report
cd ..

# Run Playwright tests and generate Allure report
cd playwright-tests
npx playwright test --config=playwright.config.ts
npx allure generate ../shared-reporting/allure-playwright-results -o ../shared-reporting/allure-playwright-report --clean
cd ..

echo "All tests complete."
echo "Selenium Allure report: shared-reporting/allure-selenium-results/index.html"
echo "Playwright Allure report: shared-reporting/allure-playwright-report/index.html"
