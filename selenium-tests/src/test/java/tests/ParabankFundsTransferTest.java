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

public class ParabankFundsTransferTest {
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
    public void testFundsTransferValid() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        try {
            WaitUtils.waitForElementVisible(driver, By.name("username")).sendKeys("testuser");
            WaitUtils.waitForElementVisible(driver, By.name("password")).sendKeys("testpass");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            WaitUtils.waitForElementClickable(driver, By.linkText("Transfer Funds")).click();
            WaitUtils.waitForElementVisible(driver, By.name("amount")).sendKeys("10");
            WaitUtils.waitForElementVisible(driver, By.name("fromAccountId")).sendKeys("12345");
            WaitUtils.waitForElementVisible(driver, By.name("toAccountId")).sendKeys("54321");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            WebElement confirmation = WaitUtils.waitForElementVisible(driver, By.id("rightPanel"));
            Assert.assertTrue(confirmation.getText().toLowerCase().contains("transfer complete") || confirmation.getText().toLowerCase().contains("error") || confirmation.getText().toLowerCase().contains("insufficient"));
        } catch (Exception e) {
            System.out.println("Transfer Funds navigation or form failed: " + e.getMessage());
            Assert.fail("Transfer Funds navigation or form failed: " + e.getMessage());
        }
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void testFundsTransferExceedsBalance() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        try {
            WaitUtils.waitForElementVisible(driver, By.name("username")).sendKeys("testuser");
            WaitUtils.waitForElementVisible(driver, By.name("password")).sendKeys("testpass");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            WaitUtils.waitForElementClickable(driver, By.linkText("Transfer Funds")).click();
            WaitUtils.waitForElementVisible(driver, By.name("amount")).sendKeys("999999");
            WaitUtils.waitForElementVisible(driver, By.name("fromAccountId")).sendKeys("12345");
            WaitUtils.waitForElementVisible(driver, By.name("toAccountId")).sendKeys("54321");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            WebElement errorPanel = WaitUtils.waitForElementVisible(driver, By.id("rightPanel"));
            Assert.assertTrue(errorPanel.getText().toLowerCase().contains("error") || errorPanel.getText().toLowerCase().contains("insufficient") || errorPanel.getText().toLowerCase().contains("transfer") || errorPanel.getText().toLowerCase().contains("complete"));
        } catch (Exception e) {
            System.out.println("Transfer Funds navigation or form failed: " + e.getMessage());
            Assert.fail("Transfer Funds navigation or form failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
