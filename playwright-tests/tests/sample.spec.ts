import { test, expect } from '@playwright/test';

test('Google homepage has title', async ({ page }) => {
  await page.goto('https://www.google.com');
  await expect(page).toHaveTitle(/Google/);
});
