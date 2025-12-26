import { test, expect, request } from '@playwright/test';
import { waitForVisible } from '../utils/wait';

test('API contract: /api/items returns expected schema and fast response', async () => {
  const api = await request.newContext();
  try {
    const start = Date.now();
    const resp = await api.get('https://yourapp.com/api/items');
    const responseTime = Date.now() - start;
    console.log('API /api/items response time:', responseTime, 'ms');
    expect(responseTime).toBeLessThan(1500);
    expect(resp.ok()).toBeTruthy();
    const data = await resp.json();
    expect(Array.isArray(data)).toBeTruthy();
    if (data.length > 0) {
      expect(data[0]).toHaveProperty('id');
      expect(data[0]).toHaveProperty('name');
    }
  } catch (e) {
    console.error('Error in API contract test:', e);
    throw e;
  }
});
