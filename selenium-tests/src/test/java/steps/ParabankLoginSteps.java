package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import pages.ParabankLoginPage;

public class ParabankLoginSteps {
    private static WebDriver driver;
    private ParabankLoginPage loginPage;

    // Standard step for navigation
    @Given("I am on the Parabank login page")
    public void i_am_on_the_login_page() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 1024));
        Hooks.setDriver(driver);
        loginPage = new ParabankLoginPage(driver);
        loginPage.open();
    }

    // Parameterized, reusable login step
    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("I should see the Accounts Overview page")
    public void i_should_see_accounts_overview() {
        Assert.assertTrue(loginPage.isAccountsOverviewDisplayed(), "Accounts Overview header not displayed");
    }

    @Then("I should see an error message")
    public void i_should_see_error_message() {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed");
    }

    // Highly reusable step for login as any user
    @Given("I login as {string} with password {string}")
    public void i_login_as_with_password(String username, String password) {
        i_am_on_the_login_page();
        i_login_with_username_and_password(username, password);
    }
    // Add more parameterized steps as needed for maintainability
}
