package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ParabankAccountOverviewPage {
    private WebDriver driver;
    private By accountsOverviewHeader = By.xpath("//h1[contains(text(),'Accounts Overview')]");
    private By errorMessage = By.cssSelector(".error");

    public ParabankAccountOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isAccountsOverviewDisplayed() {
        return driver.findElements(accountsOverviewHeader).size() > 0;
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }
}