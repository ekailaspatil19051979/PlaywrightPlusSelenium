package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class Hooks {
    private static WebDriver driver;

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        Reporter.log("Starting scenario: " + scenario.getName(), true);
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (driver != null) {
            // Attach screenshot to report if scenario failed
            if (scenario.isFailed()) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            }
            driver.quit();
        }
        Reporter.log("Finished scenario: " + scenario.getName(), true);
    }
}
