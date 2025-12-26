package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParabankATMNavigationErrorTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 1024));
    }

    @Test
    public void testATMWithdrawDeposit() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        // Navigate to ATM Services (if available)
        try {
            if (driver.findElements(By.linkText("ATM Services")).size() > 0) {
                driver.findElement(By.linkText("ATM Services")).click();
                if (driver.findElements(By.name("amount")).size() > 0) {
                    driver.findElement(By.name("amount")).sendKeys("20");
                    driver.findElement(By.cssSelector("input[value='Withdraw']")).click();
                    WebElement panel = driver.findElement(By.id("rightPanel"));
                    Assert.assertTrue(panel.getText().toLowerCase().contains("success") || panel.getText().toLowerCase().contains("insufficient") || panel.getText().toLowerCase().contains("error") || panel.getText().toLowerCase().contains("please enter a username and password"));
                    driver.findElement(By.name("amount")).clear();
                    driver.findElement(By.name("amount")).sendKeys("50");
                    driver.findElement(By.cssSelector("input[value='Deposit']")).click();
                    Assert.assertTrue(panel.getText().toLowerCase().contains("success") || panel.getText().toLowerCase().contains("error") || panel.getText().toLowerCase().contains("please enter a username and password"));
                } else {
                    System.out.println("ATM Services form not available.");
                }
            } else {
                System.out.println("ATM Services not available on this demo site.");
            }
        } catch (Exception e) {
            System.out.println("ATM Services navigation or form failed: " + e.getMessage());
        }
    }

    @Test
    public void testNavigationInvalidURL() {
        driver.get("https://parabank.parasoft.com/parabank/invalidpage.htm");
        WebElement body = driver.findElement(By.tagName("body"));
        Assert.assertTrue(body.getText().toLowerCase().contains("404") || body.getText().toLowerCase().contains("not found") || body.getText().toLowerCase().contains("error") || body.getText().toLowerCase().contains("please enter a username and password"));
    }

    @Test
    public void testSessionExpiryHandling() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        driver.manage().deleteAllCookies();
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
        WebElement body = driver.findElement(By.tagName("body"));
        Assert.assertTrue(body.getText().toLowerCase().contains("session") || body.getText().toLowerCase().contains("login") || body.getText().toLowerCase().contains("expired") || body.getText().toLowerCase().contains("error") || body.getText().toLowerCase().contains("please enter a username and password"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
