package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class CustomCommands {
    public static void logout(WebDriver driver) {
        driver.findElement(By.id("logoutBtn")).click();
    }

    public static void search(WebDriver driver, String query) {
        driver.findElement(By.id("searchBox")).sendKeys(query);
        driver.findElement(By.id("searchBtn")).click();
    }
}
