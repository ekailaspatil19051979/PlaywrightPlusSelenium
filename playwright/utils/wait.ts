import { Page, Locator, expect } from '@playwright/test';

export async function waitForVisible(page: Page, selector: string, timeout = 15000): Promise<Locator> {
  const locator = page.locator(selector);
  await expect(locator).toBeVisible({ timeout });
  return locator;
}

export async function waitForClickable(page: Page, selector: string, timeout = 15000): Promise<Locator> {
  const locator = page.locator(selector);
  await expect(locator).toBeEnabled({ timeout });
  return locator;
}

export async function waitForUrlContains(page: Page, partialUrl: string, timeout = 15000): Promise<void> {
  await expect(page).toHaveURL(new RegExp(partialUrl), { timeout });
}

export async function waitForText(page: Page, selector: string, text: string, timeout = 15000): Promise<void> {
  const locator = page.locator(selector);
  await expect(locator).toContainText(text, { timeout });
}
