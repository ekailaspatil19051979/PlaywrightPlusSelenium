package com.example.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import com.deque.axe.AXE;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;

public class AccessibilityTest {
    private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");

    @Test
    public void testAccessibility() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
        JSONArray violations = responseJSON.getJSONArray("violations");
        assert violations.length() == 0 : "Accessibility issues found: " + violations.toString();
        driver.quit();
    }
}
