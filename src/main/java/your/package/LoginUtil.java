package your.package;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginUtil {
    public static void login(WebDriver driver, String username, String password) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("loginBtn")).click();
    }
}
