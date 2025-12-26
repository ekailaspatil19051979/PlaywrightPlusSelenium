package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/parabank_account_overview.feature",
    glue = {"steps"},
    plugin = {"pretty", "html:target/cucumber-account-overview-report.html"}
)
public class ParabankAccountOverviewRunner extends AbstractTestNGCucumberTests {
}
