package stepDefinitions;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // path to your .feature files
        glue = "stepDefinitions",               // path to your step definitions
        plugin = {"pretty","summary","html:target/cucumber-report.html"},
        publish = false
)
public class RunCucumberTest {
}
