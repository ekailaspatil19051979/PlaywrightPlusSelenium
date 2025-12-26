# Playwright + Selenium Test Automation Suite

## Overview
This repository provides a hybrid test automation framework using Playwright (TypeScript) for UI and API testing, and Selenium (Java) for cross-browser UI testing. It targets demo applications such as Parabank and RESTful Booker.

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
