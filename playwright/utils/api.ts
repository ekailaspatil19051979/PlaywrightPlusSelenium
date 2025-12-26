import { request } from '@playwright/test';

export async function getUserToken(username: string, password: string) {
  const req = await request.newContext();
  const response = await req.post('https://yourapp.com/api/login', {
    data: { username, password },
  });
  const data = await response.json();
  // return data.token;
  return data;
}
