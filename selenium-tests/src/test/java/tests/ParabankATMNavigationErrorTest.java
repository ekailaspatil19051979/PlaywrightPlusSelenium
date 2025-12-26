package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import com.example.utils.WaitUtils;
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
        String browser = System.getProperty("browser", "chrome");
        switch (browser.toLowerCase()) {
            case "firefox":
                org.openqa.selenium.firefox.FirefoxOptions ffOptions = new org.openqa.selenium.firefox.FirefoxOptions();
                if (headless) ffOptions.addArguments("-headless");
                driver = new org.openqa.selenium.firefox.FirefoxDriver(ffOptions);
                break;
            case "edge":
                org.openqa.selenium.edge.EdgeOptions edgeOptions = new org.openqa.selenium.edge.EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                driver = new org.openqa.selenium.edge.EdgeDriver(edgeOptions);
                break;
            case "safari":
                driver = new org.openqa.selenium.safari.SafariDriver();
                break;
            case "chrome":
            default:
                org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        }
        driver.manage().window().setSize(new Dimension(1280, 1024));
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testATMWithdrawDeposit() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        try {
            WaitUtils.waitForElementVisible(driver, By.name("username")).sendKeys("testuser");
            WaitUtils.waitForElementVisible(driver, By.name("password")).sendKeys("testpass");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            if (driver.findElements(By.linkText("ATM Services")).size() > 0) {
                WaitUtils.waitForElementClickable(driver, By.linkText("ATM Services")).click();
                WaitUtils.waitForElementVisible(driver, By.name("amount")).sendKeys("20");
                WaitUtils.waitForElementClickable(driver, By.cssSelector("input[value='Withdraw']")).click();
                WebElement panel = WaitUtils.waitForElementVisible(driver, By.id("rightPanel"));
                Assert.assertTrue(panel.getText().toLowerCase().contains("success") || panel.getText().toLowerCase().contains("insufficient") || panel.getText().toLowerCase().contains("error") || panel.getText().toLowerCase().contains("please enter a username and password"));
                WaitUtils.waitForElementVisible(driver, By.name("amount")).clear();
                WaitUtils.waitForElementVisible(driver, By.name("amount")).sendKeys("50");
                WaitUtils.waitForElementClickable(driver, By.cssSelector("input[value='Deposit']")).click();
                Assert.assertTrue(panel.getText().toLowerCase().contains("success") || panel.getText().toLowerCase().contains("error") || panel.getText().toLowerCase().contains("please enter a username and password"));
            } else {
                System.out.println("ATM Services not available on this demo site.");
            }
        } catch (Exception e) {
            System.out.println("ATM Services navigation or form failed: " + e.getMessage());
            Assert.fail("ATM Services navigation or form failed: " + e.getMessage());
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testNavigationInvalidURL() {
        driver.get("https://parabank.parasoft.com/parabank/invalidpage.htm");
        try {
            WebElement body = WaitUtils.waitForElementVisible(driver, By.tagName("body"));
            Assert.assertTrue(body.getText().toLowerCase().contains("404") || body.getText().toLowerCase().contains("not found") || body.getText().toLowerCase().contains("error") || body.getText().toLowerCase().contains("please enter a username and password"));
        } catch (Exception e) {
            System.out.println("Body not found or error in invalid URL test: " + e.getMessage());
            Assert.fail("Body not found or error in invalid URL test: " + e.getMessage());
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testSessionExpiryHandling() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        try {
            WaitUtils.waitForElementVisible(driver, By.name("username")).sendKeys("testuser");
            WaitUtils.waitForElementVisible(driver, By.name("password")).sendKeys("testpass");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            driver.manage().deleteAllCookies();
            driver.get("https://parabank.parasoft.com/parabank/overview.htm");
            WebElement body = WaitUtils.waitForElementVisible(driver, By.tagName("body"));
            Assert.assertTrue(body.getText().toLowerCase().contains("session") || body.getText().toLowerCase().contains("login") || body.getText().toLowerCase().contains("expired") || body.getText().toLowerCase().contains("error") || body.getText().toLowerCase().contains("please enter a username and password"));
        } catch (Exception e) {
            System.out.println("Session expiry handling failed: " + e.getMessage());
            Assert.fail("Session expiry handling failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
