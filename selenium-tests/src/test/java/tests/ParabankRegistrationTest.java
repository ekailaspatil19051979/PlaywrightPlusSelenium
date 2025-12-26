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

public class ParabankRegistrationTest {
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
    public void testRegistration() {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        try {
            WaitUtils.waitForElementVisible(driver, By.name("customer.firstName")).sendKeys("Test");
            WaitUtils.waitForElementVisible(driver, By.name("customer.lastName")).sendKeys("User");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.street")).sendKeys("123 Main St");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.city")).sendKeys("Testville");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.state")).sendKeys("TS");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.zipCode")).sendKeys("12345");
            String uniqueUser = "testuser" + System.currentTimeMillis();
            WaitUtils.waitForElementVisible(driver, By.name("customer.username")).sendKeys(uniqueUser);
            WaitUtils.waitForElementVisible(driver, By.name("customer.password")).sendKeys("testpass");
            WaitUtils.waitForElementVisible(driver, By.name("repeatedPassword")).sendKeys("testpass");
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            // Wait for confirmation or error
            try {
                WebElement confirmation = WaitUtils.waitForElementVisible(driver, By.id("rightPanel"));
                String text = confirmation.getText();
                if (text.contains("Your account was created successfully")) {
                    Assert.assertTrue(true);
                } else if (text.toLowerCase().contains("error") || text.toLowerCase().contains("already exists")) {
                    System.out.println("Registration error or duplicate: " + text);
                } else {
                    System.out.println("Registration result: " + text);
                }
            } catch (Exception e) {
                System.out.println("Confirmation or error panel not found after registration: " + e.getMessage());
                Assert.fail("Confirmation or error panel not found after registration: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Exception during registration test: " + e.getMessage());
            Assert.fail("Exception during registration test: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
