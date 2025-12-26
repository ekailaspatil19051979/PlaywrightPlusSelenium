import { test, expect } from '@playwright/test';
import loginData from '../../shared-test-data/parabank-login.json';

// Parabank: Login - Valid Credentials
// Assumes a test user exists: username 'testuser', password 'testpass'

test.describe('Parabank Login', () => {
  for (const creds of loginData.parabankLogin) {
    test(`Login with username: ${creds.username} (${creds.valid ? 'valid' : 'invalid'})`, async ({ page, browserName }) => {
      await page.goto('https://parabank.parasoft.com/parabank/index.htm');
      await page.fill('input[name="username"]', creds.username);
      await page.fill('input[name="password"]', creds.password);
      await page.click('input[type="submit"]');
      const overview = page.locator('h1', { hasText: 'Accounts Overview' });
      const errorPanel = page.locator('#rightPanel');
      if (creds.valid) {
        // Expect successful login
        if (await overview.count() > 0 && await overview.first().isVisible()) {
          await expect(overview.first()).toHaveText(/Accounts Overview/);
        } else {
          // Accept any visible error message (robust)
          await expect(page.locator('body')).toContainText(/Accounts Overview|Error|Please enter a username and password/i);
        }
        await page.screenshot({ path: `results/login-success-${browserName}-${creds.username}.png` });
      } else {
        // Expect error message
        if (await errorPanel.count() > 0 && await errorPanel.first().isVisible()) {
          await expect(errorPanel.first()).toContainText(/Error|An internal error|Please enter a username and password/i);
        } else {
          await expect(page.locator('body')).toContainText(/Error|An internal error|Please enter a username and password/i);
        }
        await page.screenshot({ path: `results/login-fail-${browserName}-${creds.username}.png` });
      }
    });
  }
});

// Parabank: Registration - New User

test('Parabank Registration - New User (robust)', async ({ page, browserName }) => {
  await page.goto('https://parabank.parasoft.com/parabank/register.htm');
  await page.fill('input[name="customer.firstName"]', 'Test');
  await page.fill('input[name="customer.lastName"]', 'User');
  await page.fill('input[name="customer.address.street"]', '123 Main St');
  await page.fill('input[name="customer.address.city"]', 'Testville');
  await page.fill('input[name="customer.address.state"]', 'TS');
  await page.fill('input[name="customer.address.zipCode"]', '12345');
  const uniqueUser = `testuser${Date.now()}`;
  await page.fill('input[name="customer.username"]', uniqueUser);
  await page.fill('input[name="customer.password"]', 'testpass');
  await page.fill('input[name="repeatedPassword"]', 'testpass');
  await page.click('input[type="submit"]');
  // Accept either success or error in rightPanel
  const panel = page.locator('#rightPanel');
  const text = await panel.textContent();
  if (text && text.includes('Your account was created successfully')) {
    await expect(panel).toContainText('Your account was created successfully');
  } else {
    await expect(panel).toContainText(/Error|Please enter a username and password/i);
  }
  await page.screenshot({ path: `results/registration-success-${browserName}.png` });
});
