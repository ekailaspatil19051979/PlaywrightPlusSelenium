import { test, expect, request } from '@playwright/test';
import testData from '../../shared-test-data/restful-booker.json';

const BASE_URL = 'https://restful-booker.herokuapp.com';

test.describe('RESTful Booker API', () => {
  for (const creds of testData.auth) {
    test(`Auth - ${creds.valid ? 'Valid' : 'Invalid'} Credentials (${creds.username})`, async ({ request }) => {
      const response = await request.post(`${BASE_URL}/auth`, {
        data: { username: creds.username, password: creds.password },
      });
      if (creds.valid) {
        expect(response.ok()).toBeTruthy();
        const body = await response.json();
        expect(body.token).toBeTruthy();
      } else {
        expect(response.status()).toBe(200); // API returns 200 with error message
        const body = await response.json();
        expect(body.reason).toBe('Bad credentials');
      }
    });
  }

  for (const booking of testData.bookings) {
    test(`Create Booking - ${booking.valid ? 'Valid' : 'Invalid'} Data`, async ({ request }) => {
      const response = await request.post(`${BASE_URL}/booking`, { data: booking.valid ? booking : {} });
      if (booking.valid) {
        expect(response.ok()).toBeTruthy();
        const body = await response.json();
        expect(body.bookingid).toBeTruthy();
        expect(body.booking.firstname).toBe(booking.firstname);
      } else {
        expect(response.status()).toBe(500); // API returns 500 for missing fields
      }
    });
  }

  test('Get Booking(s)', async ({ request }) => {
    const response = await request.get(`${BASE_URL}/booking`);
    expect(response.ok()).toBeTruthy();
    const bookings = await response.json();
    expect(Array.isArray(bookings)).toBeTruthy();
  });

  test('Health Check', async ({ request }) => {
    const response = await request.get(`${BASE_URL}/ping`);
    expect(response.ok()).toBeTruthy();
    expect(await response.text()).toBe('Created');
  });
});
