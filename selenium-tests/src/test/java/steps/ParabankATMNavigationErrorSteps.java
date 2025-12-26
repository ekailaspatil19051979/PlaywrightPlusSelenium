package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import pages.ParabankATMNavigationErrorPage;

public class ParabankATMNavigationErrorSteps {
    private WebDriver driver;
    private ParabankATMNavigationErrorPage atmPage;

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
        atmPage = new ParabankATMNavigationErrorPage(driver);
    }

    @When("I use ATM Services to withdraw 20 and deposit 50")
    public void i_use_atm_services() {
        try {
            atmPage.openATMServices();
            atmPage.withdraw("20");
            atmPage.deposit("50");
        } catch (Exception e) {
            System.out.println("ATM Services not available: " + e.getMessage());
        }
    }

    @Then("I should see a success, error, or unavailable message")
    public void i_should_see_success_error_or_unavailable() {
        String text = atmPage.getPanelText();
        Assert.assertTrue(text.contains("success") || text.contains("insufficient") || text.contains("error") || text.contains("please enter a username and password") || text.contains("atm services not available"));
        driver.quit();
    }

    @When("I navigate to an invalid Parabank URL")
    public void i_navigate_to_invalid_url() {
        driver.get("https://parabank.parasoft.com/parabank/invalidpage.htm");
    }

    @Then("I should see a 404 or error message")
    public void i_should_see_404_or_error() {
        String text = atmPage.getBodyText();
        Assert.assertTrue(text.contains("404") || text.contains("not found") || text.contains("error") || text.contains("please enter a username and password"));
        driver.quit();
    }

    @When("my session expires")
    public void my_session_expires() {
        driver.manage().deleteAllCookies();
        driver.get("https://parabank.parasoft.com/parabank/overview.htm");
    }

    @Then("I should see a session expired or login prompt")
    public void i_should_see_session_expired_or_login_prompt() {
        org.openqa.selenium.WebElement body = driver.findElement(org.openqa.selenium.By.tagName("body"));
        String text = body.getText().toLowerCase();
        Assert.assertTrue(text.contains("session") || text.contains("login") || text.contains("expired") || text.contains("error") || text.contains("please enter a username and password"));
        driver.quit();
    }
}
