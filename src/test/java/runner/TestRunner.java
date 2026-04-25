package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * TestRunner — entry point for the Cucumber test suite.
 *
 * Tags:
 *   @smoke   — fast sanity checks (login, navigation)
 *   @regression — full regression suite
 *   @wip     — work-in-progress scenarios (excluded from CI by default)
 *
 * Run from Maven: mvn test -Dcucumber.filter.tags="@smoke"
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinition", "hooks"},
        tags = "not @wip",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        publish = false
)
public class TestRunner {
    // Intentionally empty — JUnit picks this up via @RunWith
}
