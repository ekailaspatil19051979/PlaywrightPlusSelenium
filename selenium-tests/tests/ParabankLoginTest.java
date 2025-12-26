
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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

@Epic("Parabank")
@Feature("Login")
public class ParabankLoginTest extends BaseAllureTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(String browser) {
        // Use WebDriverManager to handle driver binaries
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "safari":
                // SafariDriver does not require setup on Mac
                driver = new SafariDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("../shared-test-data/parabank-login.json")));
        JSONObject json = new JSONObject(content);
        JSONArray arr = json.getJSONArray("parabankLogin");
        Object[][] data = new Object[arr.length()][3];
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            data[i][0] = obj.getString("username");
            data[i][1] = obj.getString("password");
            data[i][2] = obj.getBoolean("valid");
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    @Story("Login Data Driven")
    @Description("Test login to Parabank with data-driven approach")
    public void testLogin(String username, String password, boolean valid) {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        if (valid) {
            WebElement overview = driver.findElement(By.xpath("//h1[contains(text(),'Accounts Overview')]"));
            Assert.assertTrue(overview.isDisplayed());
        } else {
            // Accept any error message in the right panel or body
            boolean errorDisplayed = false;
            try {
                WebElement error = driver.findElement(By.cssSelector(".error"));
                errorDisplayed = error.isDisplayed();
            } catch (Exception e) {
                // fallback: check for error in body
                errorDisplayed = driver.getPageSource().toLowerCase().contains("error");
            }
            Assert.assertTrue(errorDisplayed);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
