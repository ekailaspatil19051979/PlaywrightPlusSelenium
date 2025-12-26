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

public class ParabankRegistrationTest extends BaseAllureTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testRegistration() {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
        driver.findElement(By.name("customer.firstName")).sendKeys("Test");
        driver.findElement(By.name("customer.lastName")).sendKeys("User");
        driver.findElement(By.name("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.name("customer.address.city")).sendKeys("Testville");
        driver.findElement(By.name("customer.address.state")).sendKeys("TS");
        driver.findElement(By.name("customer.address.zipCode")).sendKeys("12345");
        String uniqueUser = "testuser" + System.currentTimeMillis();
        driver.findElement(By.name("customer.username")).sendKeys(uniqueUser);
        driver.findElement(By.name("customer.password")).sendKeys("testpass");
        driver.findElement(By.name("repeatedPassword")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement confirmation = driver.findElement(By.id("rightPanel"));
        Assert.assertTrue(confirmation.getText().contains("Your account was created successfully"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
