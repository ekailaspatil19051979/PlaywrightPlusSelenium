package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/parabank_funds_transfer.feature",
    glue = {"steps"},
    plugin = {"pretty", "html:target/cucumber-funds-transfer-report.html"}
)
public class ParabankFundsTransferRunner extends AbstractTestNGCucumberTests {
}
