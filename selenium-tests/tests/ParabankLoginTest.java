
package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Epic("Parabank")
@Feature("Login")
public class ParabankLoginTest extends BaseAllureTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() {
        // For demo, using Chrome only. For cross-browser, use WebDriverManager or parameterize driver.
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    @Story("Valid Login")
    @Description("Test valid login to Parabank")
    public void testValidLogin() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("testpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement overview = driver.findElement(By.xpath("//h1[contains(text(),'Accounts Overview')]"));
        Assert.assertTrue(overview.isDisplayed());
    }

    @Test
    @Story("Invalid Login")
    @Description("Test invalid login to Parabank")
    public void testInvalidLogin() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("wronguser");
        driver.findElement(By.name("password")).sendKeys("wrongpass");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        WebElement error = driver.findElement(By.cssSelector(".error"));
        Assert.assertTrue(error.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
