package com.example.utils;

import org.openqa.selenium.WebDriver;
import com.microsoft.playwright.Page;

public class NavigationUtils {
    // Selenium navigation utility
    public static void goTo(WebDriver driver, String url) {
        driver.get(url);
    }

    // Playwright navigation utility
    public static void goTo(Page page, String url) {
        page.navigate(url);
    }
}
