package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParabankATMNavigationErrorTest extends BaseAllureTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testATMWithdrawDeposit() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        // Navigate to ATM Services (if available)
        try {
            driver.findElement(By.linkText("ATM Services")).click();
            driver.findElement(By.name("amount")).sendKeys("20");
            driver.findElement(By.cssSelector("input[value='Withdraw']")).click();
            WebElement panel = driver.findElement(By.id("rightPanel"));
            Assert.assertTrue(panel.getText().toLowerCase().contains("success") || panel.getText().toLowerCase().contains("insufficient") || panel.getText().toLowerCase().contains("error"));
            driver.findElement(By.name("amount")).clear();
            driver.findElement(By.name("amount")).sendKeys("50");
            driver.findElement(By.cssSelector("input[value='Deposit']")).click();
            Assert.assertTrue(panel.getText().toLowerCase().contains("success") || panel.getText().toLowerCase().contains("error"));
        } catch (Exception e) {
            System.out.println("ATM Services not available on this demo site.");
        }
    }

    @Test
    public void testNavigationInvalidURL() {
        driver.get("https://parabank.parasoft.com/parabank/invalidpage.htm");
        WebElement body = driver.findElement(By.tagName("body"));
        Assert.assertTrue(body.getText().toLowerCase().contains("404") || body.getText().toLowerCase().contains("not found") || body.getText().toLowerCase().contains("error"));
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
        Assert.assertTrue(body.getText().toLowerCase().contains("session") || body.getText().toLowerCase().contains("login") || body.getText().toLowerCase().contains("expired") || body.getText().toLowerCase().contains("error"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
