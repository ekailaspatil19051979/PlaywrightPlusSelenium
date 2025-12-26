package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class PerformanceTest {
    private WebDriver driver;
    private long startTime;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testLoginPerformance() {
        long pageLoadStart = System.currentTimeMillis();
        driver.get("https://yourapp.com/login");
        long pageLoadTime = System.currentTimeMillis() - pageLoadStart;
        System.out.println("Login page load time: " + pageLoadTime + " ms");
        Assert.assertTrue(pageLoadTime < 2000, "Login page load too long: " + pageLoadTime + " ms");

        driver.findElement(By.id("username")).sendKeys("user");
        driver.findElement(By.id("password")).sendKeys("pass");
        startTime = System.currentTimeMillis();
        driver.findElement(By.id("loginBtn")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("dashboard"));
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Login flow duration: " + duration + " ms");
        Assert.assertTrue(duration < 3000, "Login took too long: " + duration + " ms");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
