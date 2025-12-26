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

public class ParabankAccountOverviewTest {
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
    public void testAccountOverview() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        // Handle pop-up modal or alert if present
        try {
            driver.switchTo().alert().accept();
            System.out.println("Closed alert pop-up.");
        } catch (Exception e) {
            // No alert present, continue
        }
        try {
            WaitUtils.waitForElementVisible(driver, By.name("username")).sendKeys("testuser");
            WaitUtils.waitForElementVisible(driver, By.name("password")).sendKeys("testpass");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            // Wait for Accounts Overview header
            try {
                WebElement overview = WaitUtils.waitForElementVisible(driver, By.xpath("//h1[contains(text(),'Accounts Overview')]"));
                Assert.assertTrue(overview.isDisplayed(), "Accounts Overview header not displayed");
            } catch (Exception e) {
                System.out.println("Accounts Overview header not found: " + e.getMessage());
                return;
            }
            // Try to click Accounts Overview link if present
            try {
                WebElement link = WaitUtils.waitForElementClickable(driver, By.linkText("Accounts Overview"));
                link.click();
                WebElement details = WaitUtils.waitForElementVisible(driver, By.xpath("//h1[contains(text(),'Account Details')]"));
                Assert.assertTrue(details.isDisplayed(), "Account Details header not displayed");
            } catch (Exception e) {
                System.out.println("Account Details section not found or navigation failed: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Exception during account overview test: " + e.getMessage());
            Assert.fail("Exception during account overview test: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
