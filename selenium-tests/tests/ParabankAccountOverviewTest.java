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

public class ParabankAccountOverviewTest extends BaseAllureTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testAccountOverview() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement overview = driver.findElement(By.xpath("//h1[contains(text(),'Accounts Overview')]"));
        Assert.assertTrue(overview.isDisplayed());
        driver.findElement(By.linkText("Accounts Overview")).click();
        WebElement details = driver.findElement(By.xpath("//h1[contains(text(),'Account Details')]"));
        Assert.assertTrue(details.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
