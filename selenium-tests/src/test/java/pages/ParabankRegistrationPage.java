package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ParabankRegistrationPage {
    private WebDriver driver;

    private By firstNameField = By.name("customer.firstName");
    private By lastNameField = By.name("customer.lastName");
    private By addressField = By.name("customer.address.street");
    private By cityField = By.name("customer.address.city");
    private By stateField = By.name("customer.address.state");
    private By zipField = By.name("customer.address.zipCode");
    private By usernameField = By.name("customer.username");
    private By passwordField = By.name("customer.password");
    private By confirmPasswordField = By.name("repeatedPassword");
    private By registerButton = By.cssSelector("input[type='submit']");
    private By confirmationPanel = By.id("rightPanel");

    public ParabankRegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");
    }

    public void fillRegistrationForm(String firstName, String lastName, String address, String city, String state, String zip, String username, String password) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(stateField).sendKeys(state);
        driver.findElement(zipField).sendKeys(zip);
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);
    }

    public void submit() {
        driver.findElement(registerButton).click();
    }

    public String getConfirmationText() {
        WebElement panel = driver.findElement(confirmationPanel);
        return panel.getText();
    }
}