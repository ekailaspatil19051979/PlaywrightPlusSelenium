package com.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import com.microsoft.playwright.Page;

public class LoginUtils {
    // Selenium login utility
    public static void loginSelenium(WebDriver driver, String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginBtn")).click();
    }

    // Playwright login utility
    public static void loginPlaywright(Page page, String username, String password) {
        page.fill("#username", username);
        page.fill("#password", password);
        page.click("#loginBtn");
    }
}
