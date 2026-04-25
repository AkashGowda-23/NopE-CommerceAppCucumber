package hooks;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hooks — Cucumber lifecycle callbacks.
 * PicoContainer injects BaseTest so the same driver instance is shared
 * across step definitions and hooks within one scenario.
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private final BaseTest baseTest;

    public Hooks(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        log.info("▶ Starting scenario: [{}] {}", scenario.getId(), scenario.getName());
        baseTest.initDriver();
    }

    /**
     * Captures a screenshot and attaches it to the Cucumber/Extent report
     * whenever a step fails.
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                org.openqa.selenium.WebDriver driver = baseTest.getDriver();
                byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                        .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "failure-screenshot");
                baseTest.captureScreenshot(scenario.getName());
            } catch (Exception e) {
                log.warn("Could not capture screenshot: {}", e.getMessage());
            }
        }
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        log.info("■ Finished scenario: {} — {}", scenario.getName(), scenario.getStatus());
        baseTest.tearDown();
    }
}
