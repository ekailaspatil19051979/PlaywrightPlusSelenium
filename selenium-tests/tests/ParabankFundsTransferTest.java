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

public class ParabankFundsTransferTest extends BaseAllureTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testFundsTransferValid() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        driver.findElement(By.linkText("Transfer Funds")).click();
        driver.findElement(By.name("amount")).sendKeys("10");
        driver.findElement(By.name("fromAccountId")).sendKeys("12345"); // Adjust as needed
        driver.findElement(By.name("toAccountId")).sendKeys("54321"); // Adjust as needed
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement confirmation = driver.findElement(By.id("rightPanel"));
        Assert.assertTrue(confirmation.getText().contains("Transfer Complete!"));
    }

    @Test
    public void testFundsTransferExceedsBalance() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        driver.findElement(By.linkText("Transfer Funds")).click();
        driver.findElement(By.name("amount")).sendKeys("999999");
        driver.findElement(By.name("fromAccountId")).sendKeys("12345"); // Adjust as needed
        driver.findElement(By.name("toAccountId")).sendKeys("54321"); // Adjust as needed
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement errorPanel = driver.findElement(By.id("rightPanel"));
        Assert.assertTrue(errorPanel.getText().toLowerCase().contains("error") || errorPanel.getText().toLowerCase().contains("insufficient"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
