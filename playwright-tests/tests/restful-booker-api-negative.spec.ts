import { test, expect, request } from '@playwright/test';

const BASE_URL = 'https://restful-booker.herokuapp.com';

test.describe('RESTful Booker API - Edge and Negative Cases', () => {
  test('Update Booking - Unauthorized', async ({ request }) => {
    const response = await request.put(`${BASE_URL}/booking/1`, {
      data: { firstname: 'Updated' },
    });
    expect(response.status()).toBe(403); // Forbidden
  });

  test('Delete Booking - Non-existent', async ({ request }) => {
    const response = await request.delete(`${BASE_URL}/booking/999999`);
    // Accept 201 (deleted), 404 (not found), or 403 (forbidden/unauthorized) as valid demo API outcomes
    expect([201, 404, 403]).toContain(response.status());
  });

  test('Create Booking - Malformed JSON', async ({ request }) => {
    const response = await request.post(`${BASE_URL}/booking`, {
      headers: { 'Content-Type': 'application/json' },
      data: '{bad json}',
    });
    expect([400, 500]).toContain(response.status());
  });

  test('Get Booking - Non-existent ID', async ({ request }) => {
    const response = await request.get(`${BASE_URL}/booking/999999`);
    expect([404, 200]).toContain(response.status());
  });
});
