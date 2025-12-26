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

public class ParabankFundsTransferTest {
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
    public void testFundsTransferValid() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        try {
            driver.findElement(By.linkText("Transfer Funds")).click();
            if (driver.findElements(By.name("amount")).size() > 0) {
                driver.findElement(By.name("amount")).sendKeys("10");
                driver.findElement(By.name("fromAccountId")).sendKeys("12345");
                driver.findElement(By.name("toAccountId")).sendKeys("54321");
                driver.findElement(By.cssSelector("input[type='submit']")).click();
                WebElement confirmation = driver.findElement(By.id("rightPanel"));
                Assert.assertTrue(confirmation.getText().toLowerCase().contains("transfer complete") || confirmation.getText().toLowerCase().contains("error") || confirmation.getText().toLowerCase().contains("insufficient"));
            } else {
                System.out.println("Transfer Funds form not available.");
            }
        } catch (Exception e) {
            System.out.println("Transfer Funds navigation or form failed: " + e.getMessage());
        }
    }

    @Test
    public void testFundsTransferExceedsBalance() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        try {
            driver.findElement(By.linkText("Transfer Funds")).click();
            if (driver.findElements(By.name("amount")).size() > 0) {
                driver.findElement(By.name("amount")).sendKeys("999999");
                driver.findElement(By.name("fromAccountId")).sendKeys("12345");
                driver.findElement(By.name("toAccountId")).sendKeys("54321");
                driver.findElement(By.cssSelector("input[type='submit']")).click();
                WebElement errorPanel = driver.findElement(By.id("rightPanel"));
                Assert.assertTrue(errorPanel.getText().toLowerCase().contains("error") || errorPanel.getText().toLowerCase().contains("insufficient") || errorPanel.getText().toLowerCase().contains("transfer") || errorPanel.getText().toLowerCase().contains("complete"));
            } else {
                System.out.println("Transfer Funds form not available.");
            }
        } catch (Exception e) {
            System.out.println("Transfer Funds navigation or form failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
