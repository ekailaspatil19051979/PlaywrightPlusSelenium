package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SampleSeleniumTest extends BaseAllureTest {
    @Test
    public void testGoogleHomePage() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
        Assert.assertTrue(driver.getTitle().contains("Google"));
        driver.quit();
    }
}
