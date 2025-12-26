package your.package;

import org.openqa.selenium.WebDriver;

public class NavigationUtil {
    public static void goToDashboard(WebDriver driver) {
        driver.get("https://yourapp.com/dashboard");
    }
}
