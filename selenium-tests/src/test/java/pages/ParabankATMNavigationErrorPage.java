package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ParabankATMNavigationErrorPage {
    private WebDriver driver;
    private By atmServicesLink = By.linkText("ATM Services");
    private By amountField = By.name("amount");
    private By withdrawButton = By.cssSelector("input[value='Withdraw']");
    private By depositButton = By.cssSelector("input[value='Deposit']");
    private By rightPanel = By.id("rightPanel");
    private By body = By.tagName("body");

    public ParabankATMNavigationErrorPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openATMServices() {
        if (driver.findElements(atmServicesLink).size() > 0) {
            driver.findElement(atmServicesLink).click();
        }
    }

    public void withdraw(String amount) {
        if (driver.findElements(amountField).size() > 0) {
            driver.findElement(amountField).sendKeys(amount);
            driver.findElement(withdrawButton).click();
        }
    }

    public void deposit(String amount) {
        if (driver.findElements(amountField).size() > 0) {
            driver.findElement(amountField).clear();
            driver.findElement(amountField).sendKeys(amount);
            driver.findElement(depositButton).click();
        }
    }

    public String getPanelText() {
        return driver.findElement(rightPanel).getText().toLowerCase();
    }

    public String getBodyText() {
        return driver.findElement(body).getText().toLowerCase();
    }
}