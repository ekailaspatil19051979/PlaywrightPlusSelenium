package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/parabank_registration.feature",
    glue = {"steps"},
    plugin = {"pretty", "html:target/cucumber-registration-report.html"}
)
public class ParabankRegistrationRunner extends AbstractTestNGCucumberTests {
}
