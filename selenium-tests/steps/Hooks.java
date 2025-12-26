package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    private static WebDriver driver;

    // Allow step classes to access driver
    public static void setDriver(WebDriver d) { driver = d; }
    public static WebDriver getDriver() { return driver; }

    @Before
    public void setUp(Scenario scenario) {
        // Example: log scenario tags for filtering
        System.out.println("Starting scenario: " + scenario.getName() + " Tags: " + scenario.getSourceTagNames());
        // Add waits, retry logic, or environment setup here
    }

    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot on failure
        if (scenario.isFailed() && driver != null) {
            // ...screenshot logic here...
            System.out.println("Scenario failed: " + scenario.getName());
        }
        // Quit WebDriver
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
