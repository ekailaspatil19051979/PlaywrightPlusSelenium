package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import pages.ParabankRegistrationPage;

public class ParabankRegistrationSteps {
    private WebDriver driver;
    private ParabankRegistrationPage registrationPage;
    private String uniqueUser;

    @Given("I am on the Parabank registration page")
    public void i_am_on_registration_page() {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        ChromeOptions options = new ChromeOptions();
        if (headless) options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(1280, 1024));
        registrationPage = new ParabankRegistrationPage(driver);
        registrationPage.open();
    }

    @When("I register with unique credentials")
    public void i_register_with_unique_credentials() {
        uniqueUser = "testuser" + System.currentTimeMillis();
        registrationPage.fillRegistrationForm(
            "Test", "User", "123 Main St", "Testville", "TS", "12345", uniqueUser, "testpass"
        );
        registrationPage.submit();
    }

    @Then("I should see a registration confirmation or error")
    public void i_should_see_registration_confirmation_or_error() {
        String text = registrationPage.getConfirmationText();
        Assert.assertTrue(text.contains("Your account was created successfully") || text.toLowerCase().contains("error") || text.toLowerCase().contains("already exists"));
        driver.quit();
    }
}
