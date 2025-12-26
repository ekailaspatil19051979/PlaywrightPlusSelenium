import { defineConfig, devices } from '@playwright/test';
import * as fs from 'fs';

// Load environment config
const envConfig = JSON.parse(fs.readFileSync('../shared-config/browser-matrix.json', 'utf-8'));

export default defineConfig({
  projects: envConfig.browsers.map((browser: any) => ({
    name: browser.name,
    use: { ...devices[browser.name + 'Desktop'], headless: true },
  })),
  reporter: [
    ['list'],
    ['html', { outputFolder: '../shared-reporting/playwright-report', open: 'never' }],
    ['allure-playwright', { outputFolder: '../shared-reporting/allure-playwright-results' }]
  ],
  outputDir: 'results/',
  retries: 1,
  use: {
    trace: 'on-first-retry',
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
  },
});
