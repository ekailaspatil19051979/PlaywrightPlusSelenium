import { test, expect, request } from '@playwright/test';

const BASE_URL = 'https://restful-booker.herokuapp.com';

test.describe('RESTful Booker API', () => {
  test('Auth - Valid Credentials', async ({ request }) => {
    const response = await request.post(`${BASE_URL}/auth`, {
      data: { username: 'admin', password: 'password123' },
    });
    expect(response.ok()).toBeTruthy();
    const body = await response.json();
    expect(body.token).toBeTruthy();
  });

  test('Auth - Invalid Credentials', async ({ request }) => {
    const response = await request.post(`${BASE_URL}/auth`, {
      data: { username: 'invalid', password: 'wrong' },
    });
    expect(response.status()).toBe(200); // API returns 200 with error message
    const body = await response.json();
    expect(body.reason).toBe('Bad credentials');
  });

  test('Create Booking - Valid Data', async ({ request }) => {
    const bookingData = {
      firstname: 'Jim',
      lastname: 'Brown',
      totalprice: 111,
      depositpaid: true,
      bookingdates: { checkin: '2023-01-01', checkout: '2023-01-02' },
      additionalneeds: 'Breakfast',
    };
    const response = await request.post(`${BASE_URL}/booking`, { data: bookingData });
    expect(response.ok()).toBeTruthy();
    const body = await response.json();
    expect(body.bookingid).toBeTruthy();
    expect(body.booking.firstname).toBe('Jim');
  });

  test('Create Booking - Invalid Data', async ({ request }) => {
    const response = await request.post(`${BASE_URL}/booking`, { data: {} });
    expect(response.status()).toBe(500); // API returns 500 for missing fields
  });

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
