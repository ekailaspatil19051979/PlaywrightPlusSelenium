package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/parabank_atm_navigation_error.feature",
    glue = {"steps"},
    plugin = {"pretty", "html:target/cucumber-atm-navigation-error-report.html"}
)
public class ParabankATMNavigationErrorRunner extends AbstractTestNGCucumberTests {
}
