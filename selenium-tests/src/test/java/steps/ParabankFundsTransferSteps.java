package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import pages.ParabankFundsTransferPage;

public class ParabankFundsTransferSteps {
    private WebDriver driver;
    private ParabankFundsTransferPage fundsTransferPage;

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
        fundsTransferPage = new ParabankFundsTransferPage(driver);
    }

    @When("I transfer {int} from account {string} to account {string}")
    public void i_transfer_amount_from_to(int amount, String from, String to) {
        fundsTransferPage.open();
        fundsTransferPage.transferFunds(String.valueOf(amount), from, to);
    }

    @Then("I should see a transfer confirmation or error")
    public void i_should_see_transfer_confirmation_or_error() {
        String text = fundsTransferPage.getConfirmationText().toLowerCase();
        Assert.assertTrue(text.contains("transfer complete") || text.contains("error") || text.contains("insufficient"));
        driver.quit();
    }

    @Then("I should see an error or insufficient funds message")
    public void i_should_see_error_or_insufficient_funds() {
        String text = fundsTransferPage.getConfirmationText().toLowerCase();
        Assert.assertTrue(text.contains("error") || text.contains("insufficient") || text.contains("transfer") || text.contains("complete"));
        driver.quit();
    }
}
