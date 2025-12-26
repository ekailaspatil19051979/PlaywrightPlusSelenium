import { test, expect, request } from '@playwright/test';
import { login } from '../utils/login';
import { waitForVisible, waitForClickable } from '../utils/wait';

test('Create item via API and verify via UI', async ({ page }) => {
  const api = await request.newContext();
  const createResp = await api.post('https://yourapp.com/api/items', {
    data: { name: 'TestItem' },
    headers: { Authorization: 'Bearer your_token' }
  });
  expect(createResp.ok()).toBeTruthy();
  const item = await createResp.json();

  await page.goto('https://yourapp.com/login');
  try {
    await waitForVisible(page, '#username');
    await login(page, 'user', 'pass');
    await waitForVisible(page, '#loginBtn');
    await page.goto('https://yourapp.com/items');
    await waitForVisible(page, `text=${item.name}`);
  } catch (e) {
    console.error('Error in UI verification:', e);
    throw e;
  }
});
