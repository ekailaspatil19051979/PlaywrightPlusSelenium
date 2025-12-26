package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.example.utils.WaitUtils;
import org.openqa.selenium.By;

public class SampleSeleniumTest {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testGoogleHomePage() {
        String browser = System.getProperty("browser", "chrome");
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new org.openqa.selenium.firefox.FirefoxDriver();
                break;
            case "edge":
                driver = new org.openqa.selenium.edge.EdgeDriver();
                break;
            case "safari":
                driver = new org.openqa.selenium.safari.SafariDriver();
                break;
            case "chrome":
            default:
                driver = new ChromeDriver();
        }
        try {
            driver.get("https://www.google.com");
            WaitUtils.waitForElementVisible(driver, By.name("q"));
            Assert.assertTrue(driver.getTitle().contains("Google"));
        } catch (Exception e) {
            System.out.println("Exception during Google home page test: " + e.getMessage());
            Assert.fail("Exception during Google home page test: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
