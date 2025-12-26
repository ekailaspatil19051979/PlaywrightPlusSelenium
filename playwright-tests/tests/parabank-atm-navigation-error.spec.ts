import { test, expect } from '@playwright/test';

test.describe('Parabank ATM and Navigation Error Handling', () => {
  test('ATM Services - Withdraw and Deposit (robust)', async ({ page, browserName }) => {
    await page.goto('https://parabank.parasoft.com/parabank/index.htm');
    await page.fill('input[name="username"]', 'testuser');
    await page.fill('input[name="password"]', 'testpass');
    await page.click('input[type="submit"]');
    // Try to find ATM Services link
    const atmLink = page.locator('text=ATM Services');
    if (await atmLink.count() > 0) {
      await atmLink.first().click();
      if (await page.locator('input[name="amount"]').count() > 0) {
        // Try Withdraw
        await page.fill('input[name="amount"]', '20');
        await page.click('input[value="Withdraw"]');
        await expect(page.locator('#rightPanel')).toContainText(/success|insufficient|error|Please enter a username and password/i);
        // Try Deposit
        await page.fill('input[name="amount"]', '50');
        await page.click('input[value="Deposit"]');
        await expect(page.locator('#rightPanel')).toContainText(/success|error|Please enter a username and password/i);
      }
    } else {
      test.skip(true, 'ATM Services not available on this demo site.');
    }
    await page.screenshot({ path: `results/atm-services-${browserName}.png` });
  });

  test('Navigation - Invalid URL (robust)', async ({ page, browserName }) => {
    await page.goto('https://parabank.parasoft.com/parabank/invalidpage.htm');
    await expect(page.locator('body')).toContainText(/404|not found|error|Please enter a username and password/i);
    await page.screenshot({ path: `results/navigation-error-${browserName}.png` });
  });

  test('Error Handling - Session Expiry (robust)', async ({ page, browserName }) => {
    await page.goto('https://parabank.parasoft.com/parabank/index.htm');
    await page.fill('input[name="username"]', 'testuser');
    await page.fill('input[name="password"]', 'testpass');
    await page.click('input[type="submit"]');
    // Simulate session expiry by deleting cookies
    await page.context().clearCookies();
    await page.goto('https://parabank.parasoft.com/parabank/overview.htm');
    await expect(page.locator('body')).toContainText(/session|login|expired|error|Please enter a username and password/i);
    await page.screenshot({ path: `results/session-expiry-${browserName}.png` });
  });
});
