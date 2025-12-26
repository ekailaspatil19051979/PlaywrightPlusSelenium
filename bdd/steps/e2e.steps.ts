import { Given, When, Then } from '@cucumber/cucumber';
import { expect } from '@playwright/test';
import { login } from '../../playwright/utils/login';

let itemName: string;

Given('I create an item named {string} via API', async function (name) {
  const api = await this.request.newContext();
  const resp = await api.post('https://yourapp.com/api/items', {
    data: { name },
    headers: { Authorization: 'Bearer your_token' }
  });
  expect(resp.ok()).toBeTruthy();
  itemName = name;
});

When('I login as {string} with password {string}', async function (user, pass) {
  await this.page.goto('https://yourapp.com/login');
  await login(this.page, user, pass);
});

When('I navigate to the items page', async function () {
  await this.page.goto('https://yourapp.com/items');
});

Then('I should see {string} in the list', async function (name) {
  await expect(this.page.locator(`text=${name}`)).toBeVisible();
});
