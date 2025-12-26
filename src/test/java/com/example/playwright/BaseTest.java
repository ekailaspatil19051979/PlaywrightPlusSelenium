package com.example.playwright;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;

public abstract class BaseTest {
    // ...existing code...

    protected void waitForSelector(Page page, String selector, int timeoutMs) {
        try {
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(timeoutMs));
        } catch (TimeoutError e) {
            throw new RuntimeException("Selector not found: " + selector, e);
        }
    }

    // ...existing code...
}