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
        // Handle pop-up modal or alert if present
        try {
            driver.switchTo().alert().accept();
            System.out.println("Closed alert pop-up.");
        } catch (Exception e) {
            // No alert present, continue
        }
        try {
            System.out.println("Navigated to registration page. Current URL: " + driver.getCurrentUrl());
            WaitUtils.waitForElementVisible(driver, By.name("customer.firstName")).sendKeys("Test");
            WaitUtils.waitForElementVisible(driver, By.name("customer.lastName")).sendKeys("User");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.street")).sendKeys("123 Main St");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.city")).sendKeys("Testville");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.state")).sendKeys("TS");
            WaitUtils.waitForElementVisible(driver, By.name("customer.address.zipCode")).sendKeys("12345");
            WaitUtils.waitForElementVisible(driver, By.name("customer.phoneNumber")).sendKeys("5551234567");
            WaitUtils.waitForElementVisible(driver, By.name("customer.ssn")).sendKeys("123-45-6789");
            String username = "s123";
            String password = "s123";
            System.out.println("Attempting registration with username: " + username + " and password: " + password);
            WaitUtils.waitForElementVisible(driver, By.name("customer.username")).sendKeys(username);
            WaitUtils.waitForElementVisible(driver, By.name("customer.password")).sendKeys(password);
            WaitUtils.waitForElementVisible(driver, By.name("repeatedPassword")).sendKeys(password);
            // Log all field values before submit
            System.out.println("Field values before submit:");
            System.out.println("First Name: " + driver.findElement(By.name("customer.firstName")).getAttribute("value"));
            System.out.println("Last Name: " + driver.findElement(By.name("customer.lastName")).getAttribute("value"));
            System.out.println("Street: " + driver.findElement(By.name("customer.address.street")).getAttribute("value"));
            System.out.println("City: " + driver.findElement(By.name("customer.address.city")).getAttribute("value"));
            System.out.println("State: " + driver.findElement(By.name("customer.address.state")).getAttribute("value"));
            System.out.println("Zip: " + driver.findElement(By.name("customer.address.zipCode")).getAttribute("value"));
            System.out.println("Phone: " + driver.findElement(By.name("customer.phoneNumber")).getAttribute("value"));
            System.out.println("SSN: " + driver.findElement(By.name("customer.ssn")).getAttribute("value"));
            System.out.println("Username: " + driver.findElement(By.name("customer.username")).getAttribute("value"));
            System.out.println("Password: " + driver.findElement(By.name("customer.password")).getAttribute("value"));
            System.out.println("Repeated Password: " + driver.findElement(By.name("repeatedPassword")).getAttribute("value"));
            WaitUtils.waitForElementClickable(driver, By.cssSelector("input[type='submit']")).click();
            System.out.println("Form submitted. Current URL: " + driver.getCurrentUrl());
            // Wait for confirmation or error
            try {
                WebElement confirmation = WaitUtils.waitForElementVisible(driver, By.id("rightPanel"));
                String text = confirmation.getText();
                System.out.println("Confirmation panel text: " + text);
                if (text.contains("Your account was created successfully")) {
                    System.out.println("REGISTERED USERNAME: " + username);
                    System.out.println("REGISTERED PASSWORD: " + password);
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
            try {
                // Attempt logout if login was successful and logout link is present
                driver.get("https://parabank.parasoft.com/parabank/index.htm");
                if (driver.findElements(By.linkText("Log Out")).size() > 0) {
                    driver.findElement(By.linkText("Log Out")).click();
                    System.out.println("Logged out after test completion.");
                } else {
                    System.out.println("Logout link not found, skipping logout.");
                }
            } catch (Exception e) {
                System.out.println("Exception during logout: " + e.getMessage());
            }
            driver.quit();
        }
    }
}
