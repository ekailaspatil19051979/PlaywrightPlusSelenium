import { Page } from '@playwright/test';

export async function goToDashboard(page: Page) {
  await page.goto('https://yourapp.com/dashboard');
}
