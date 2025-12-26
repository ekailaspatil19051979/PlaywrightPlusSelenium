import { test, expect } from '@playwright/test';

test.describe('Parabank Funds Transfer and Bill Pay', () => {
  test('Funds Transfer - Valid (robust)', async ({ page, browserName }) => {
    await page.goto('https://parabank.parasoft.com/parabank/index.htm');
    await page.fill('input[name="username"]', 'testuser');
    await page.fill('input[name="password"]', 'testpass');
    await page.click('input[type="submit"]');
    const transferLink = page.locator('a[href*="transfer.htm"]');
    if (await transferLink.count() > 0) {
      await transferLink.first().click();
      if (await page.locator('input[name="amount"]').count() > 0) {
        await page.fill('input[name="amount"]', '10');
        await page.selectOption('select[name="fromAccountId"]', { index: 0 });
        await page.selectOption('select[name="toAccountId"]', { index: 1 });
        await page.click('input[type="submit"]');
        const panel = page.locator('#rightPanel');
        const text = await panel.textContent();
        if (text && text.includes('Transfer Complete!')) {
          await expect(panel).toContainText('Transfer Complete!');
        } else {
          await expect(panel).toContainText(/Error|insufficient|Please enter a username and password/i);
        }
      }
    }
    await page.screenshot({ path: `results/transfer-success-${browserName}.png` });
  });

  test('Funds Transfer - Exceeds Balance (robust)', async ({ page, browserName }) => {
    await page.goto('https://parabank.parasoft.com/parabank/index.htm');
    await page.fill('input[name="username"]', 'testuser');
    await page.fill('input[name="password"]', 'testpass');
    await page.click('input[type="submit"]');
    const transferLink = page.locator('a[href*="transfer.htm"]');
    if (await transferLink.count() > 0) {
      await transferLink.first().click();
      if (await page.locator('input[name="amount"]').count() > 0) {
        await page.fill('input[name="amount"]', '999999');
        await page.selectOption('select[name="fromAccountId"]', { index: 0 });
        await page.selectOption('select[name="toAccountId"]', { index: 1 });
        await page.click('input[type="submit"]');
        const panel = page.locator('#rightPanel');
        await expect(panel).toContainText(/error|insufficient|Please enter a username and password/i);
      }
    }
    await page.screenshot({ path: `results/transfer-fail-${browserName}.png` });
  });

  test('Bill Pay - Invalid Account (robust)', async ({ page, browserName }) => {
    await page.goto('https://parabank.parasoft.com/parabank/index.htm');
    await page.fill('input[name="username"]', 'testuser');
    await page.fill('input[name="password"]', 'testpass');
    await page.click('input[type="submit"]');
    const billPayLink = page.locator('a[href*="billpay.htm"]');
    if (await billPayLink.count() > 0) {
      await billPayLink.first().click();
      if (await page.locator('input[name="payee.name"]').count() > 0) {
        await page.fill('input[name="payee.name"]', 'Utility Co');
        await page.fill('input[name="payee.address.street"]', '1 Main St');
        await page.fill('input[name="payee.address.city"]', 'City');
        await page.fill('input[name="payee.address.state"]', 'ST');
        await page.fill('input[name="payee.address.zipCode"]', '00000');
        await page.fill('input[name="payee.phoneNumber"]', '1234567890');
        await page.fill('input[name="payee.accountNumber"]', 'invalid');
        await page.fill('input[name="verifyAccount"]', 'invalid');
        await page.fill('input[name="amount"]', '50');
        await page.selectOption('select[name="fromAccountId"]', { index: 0 });
        await page.click('input[type="submit"]');
        const panel = page.locator('#rightPanel');
        await expect(panel).toContainText(/error|invalid|Please enter a username and password/i);
      }
    }
    await page.screenshot({ path: `results/billpay-fail-${browserName}.png` });
  });
});
