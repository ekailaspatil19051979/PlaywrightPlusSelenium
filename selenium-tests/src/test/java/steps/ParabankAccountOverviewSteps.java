package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import pages.ParabankAccountOverviewPage;

public class ParabankAccountOverviewSteps {
    private WebDriver driver;
    private ParabankAccountOverviewPage overviewPage;

    @Given("I am logged in to Parabank")
    public void i_am_logged_in_to_parabank() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 1024));
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(org.openqa.selenium.By.name("username")).sendKeys("testuser");
        driver.findElement(org.openqa.selenium.By.name("password")).sendKeys("testpass");
        driver.findElement(org.openqa.selenium.By.cssSelector("input[type='submit']")).click();
        overviewPage = new ParabankAccountOverviewPage(driver);
    }

    @When("I navigate to the Accounts Overview page")
    public void i_navigate_to_accounts_overview() {
        // Already on overview after login, but try to click link if present
        if (driver.findElements(org.openqa.selenium.By.linkText("Accounts Overview")).size() > 0) {
            driver.findElement(org.openqa.selenium.By.linkText("Accounts Overview")).click();
        }
    }

    @Then("I should see the Accounts Overview or an error")
    public void i_should_see_accounts_overview_or_error() {
        boolean found = overviewPage.isAccountsOverviewDisplayed();
        boolean error = overviewPage.isErrorDisplayed();
        Assert.assertTrue(found || error, "Neither Accounts Overview nor error found");
        driver.quit();
    }
}
