# Advanced BDD Usage Examples

## 1. Parameterized Login Step (Selenium + Cucumber)

**Feature:**
```gherkin
Scenario: Login with different users
  Given I login as "testuser" with password "testpass"
  Then I should see the Accounts Overview page

Scenario: Login with invalid credentials
  Given I login as "wronguser" with password "wrongpass"
  Then I should see an error message
```

**Step Definition:**
```java
@Given("I login as {string} with password {string}")
public void i_login_as_with_password(String username, String password) {
    // ...see ParabankLoginSteps.java...
}
```

---

## 2. Parameterized Registration Step

**Feature:**
```gherkin
Scenario: Register with custom data
  Given I am on the Parabank registration page
  When I register with first name "Alice", last name "Smith", username "alice123", and password "mypassword"
  Then I should see a registration confirmation or error
```

**Step Definition:**
```java
@When("I register with first name {string}, last name {string}, username {string}, and password {string}")
public void i_register_with_params(String first, String last, String username, String password) {
    // ...see ParabankRegistrationSteps.java...
}
```

---

## 3. Custom Hooks for Reporting

- Screenshots are automatically attached to Allure reports on failure.
- Logs are output for each scenario start/finish.

**Hooks.java:**
```java
@Before
public void beforeScenario(Scenario scenario) {
    Reporter.log("Starting scenario: " + scenario.getName(), true);
}

@After
public void afterScenario(Scenario scenario) {
    if (driver != null) {
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }
        driver.quit();
    }
    Reporter.log("Finished scenario: " + scenario.getName(), true);
}
```

---

## 4. Allure Reporting

- Run tests as usual (e.g., `mvn test` for Selenium, `npx playwright test` for Playwright).
- Generate and view reports:
  - Selenium: `mvn allure:serve`
  - Playwright: `npx playwright show-report ../shared-reporting/playwright-report`

---

## 5. Playwright BDD-like Example (TypeScript)

```typescript
test('Login with parameterized data', async ({ page }) => {
  await page.goto('https://parabank.parasoft.com/parabank/index.htm');
  await page.fill('input[name="username"]', 'testuser');
  await page.fill('input[name="password"]', 'testpass');
  await page.click('input[type="submit"]');
  await expect(page.locator('h1', { hasText: 'Accounts Overview' })).toBeVisible();
});
```

---

For more, see the `steps/` and `features/` folders for real-world usage.
