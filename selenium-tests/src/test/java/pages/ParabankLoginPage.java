package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ParabankLoginPage {
    private WebDriver driver;

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("input[type='submit']");
    private By errorMessage = By.cssSelector(".error");
    private By accountsOverviewHeader = By.xpath("//h1[contains(text(),'Accounts Overview')]");

    public ParabankLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public boolean isAccountsOverviewDisplayed() {
        return driver.findElements(accountsOverviewHeader).size() > 0;
    }
}