package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ParabankFundsTransferPage {
    private WebDriver driver;
    private By transferFundsLink = By.linkText("Transfer Funds");
    private By amountField = By.name("amount");
    private By fromAccountDropdown = By.name("fromAccountId");
    private By toAccountDropdown = By.name("toAccountId");
    private By transferButton = By.cssSelector("input[type='submit']");
    private By confirmationPanel = By.id("rightPanel");

    public ParabankFundsTransferPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        if (driver.findElements(transferFundsLink).size() > 0) {
            driver.findElement(transferFundsLink).click();
        }
    }

    public void transferFunds(String amount, String from, String to) {
        if (driver.findElements(amountField).size() > 0) {
            driver.findElement(amountField).sendKeys(amount);
            driver.findElement(fromAccountDropdown).sendKeys(from);
            driver.findElement(toAccountDropdown).sendKeys(to);
            driver.findElement(transferButton).click();
        }
    }

    public String getConfirmationText() {
        return driver.findElement(confirmationPanel).getText();
    }
}