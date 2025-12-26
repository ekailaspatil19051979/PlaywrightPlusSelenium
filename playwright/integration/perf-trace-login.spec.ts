import { test, expect } from '@playwright/test';

// Example performance test using Playwright trace

test('Performance: Trace login flow', async ({ page, context }) => {
  await context.tracing.start({ screenshots: true, snapshots: true });
  await page.goto('https://yourapp.com/login');
  await page.fill('#username', 'user');
  await page.fill('#password', 'pass');
  await page.click('#loginBtn');
  await page.waitForURL('**/dashboard');
  await context.tracing.stop({ path: 'trace-login.zip' });
  // Optionally, add assertions for performance metrics if needed
  expect(page.url()).toContain('dashboard');
});
