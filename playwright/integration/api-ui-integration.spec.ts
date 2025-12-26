import { test, expect } from '@playwright/test';
import { getUserToken } from '../utils/api';
import { login } from '../utils/login';
import { waitForVisible, waitForUrlContains } from '../utils/wait';

test('API & UI Integration Example', async ({ page }) => {
  const apiData = await getUserToken('user', 'pass');
  // ...use apiData.token if needed...
  await page.goto('https://yourapp.com/login');
  try {
    await waitForVisible(page, '#username');
    await login(page, 'user', 'pass');
    await waitForVisible(page, '#loginBtn');
    await waitForUrlContains(page, 'dashboard');
  } catch (e) {
    console.error('Error in API & UI integration test:', e);
    throw e;
  }
});
