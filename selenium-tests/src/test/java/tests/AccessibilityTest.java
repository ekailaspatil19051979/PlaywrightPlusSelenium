package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.deque.axe.AXE;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;

public class AccessibilityTest {
    private WebDriver driver;
    private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginPageAccessibility() {
        driver.get("https://yourapp.com/login");
        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();
        JSONArray violations = responseJSON.getJSONArray("violations");
        if (violations.length() == 0) {
            System.out.println("No accessibility violations found");
        } else {
            AXE.writeResults("login-accessibility", responseJSON);
        }
        Assert.assertEquals(violations.length(), 0, "Accessibility issues found: " + violations);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
