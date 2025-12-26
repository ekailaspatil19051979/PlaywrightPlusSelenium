import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  // ...existing code...
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
    {
      name: 'edge',
      use: { channel: 'msedge' },
    },
  ],
  // ...existing code...
});