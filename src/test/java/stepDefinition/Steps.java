package stepDefinition;

import base.BaseTest;
import base.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import PageObject.Login;

/**
 * Steps — Cucumber step definitions for the NopCommerce admin portal.
 * BaseTest is injected via PicoContainer (constructor injection).
 */
public class Steps {

    private static final Logger log = LogManager.getLogger(Steps.class);

    private final BaseTest baseTest;
    private WebDriver driver;
    private Login loginPage;

    /** PicoContainer injects BaseTest (same instance as Hooks). */
    public Steps(BaseTest baseTest) {
        this.baseTest = baseTest;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Browser lifecycle
    // ──────────────────────────────────────────────────────────────────────────

    @Given("user Launch chrome browser")
    public void user_launch_chrome_browser() {
        // Driver is already initialised by Hooks#beforeScenario.
        // We just obtain the reference here for use in subsequent steps.
        driver = baseTest.getDriver();
        loginPage = new Login(driver);
        log.info("WebDriver reference obtained in step definitions.");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Navigation
    // ──────────────────────────────────────────────────────────────────────────

    @When("user opens URL {string}")
    public void user_opens_url(String url) {
        // Allow the feature file to use a placeholder; real URL comes from config
        String targetUrl = url.startsWith("$")
                ? ConfigReader.get(url.substring(1))
                : url;
        driver.get(targetUrl);
        log.info("Navigated to {}", targetUrl);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Login
    // ──────────────────────────────────────────────────────────────────────────

    @When("user enters Email as {string} and Password as {string}")
    public void user_enters_email_as_and_password_as(String email, String password) {
        // Support config-key substitution so plain credentials never live in feature files:
        //   And user enters Email as "$admin.email" and Password as "$admin.password"
        String resolvedEmail    = email.startsWith("$")    ? ConfigReader.get(email.substring(1))    : email;
        String resolvedPassword = password.startsWith("$") ? ConfigReader.get(password.substring(1)) : password;

        loginPage.enteremail(resolvedEmail);
        loginPage.password(resolvedPassword);
        log.info("Credentials entered for user: {}", resolvedEmail);
    }

    @When("Click on the login")
    public void click_on_the_login() {
        loginPage.submit();
        log.info("Login button clicked.");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Assertions
    // ──────────────────────────────────────────────────────────────────────────

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        baseTest.getWait().until(ExpectedConditions.titleContains(expectedTitle));
        String actualTitle = driver.getTitle();
        Assert.assertTrue(
                "Expected title to contain '" + expectedTitle + "' but was '" + actualTitle + "'",
                actualTitle.contains(expectedTitle)
        );
        log.info("Page title verified: '{}'", actualTitle);
    }

    @Then("page title should be {string}")
    public void page_title_should_be(String expectedTitle) {
        the_page_title_should_be(expectedTitle);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Logout
    // ──────────────────────────────────────────────────────────────────────────

    @When("the user click on the logout link")
    public void the_user_click_on_the_logout_link() {
        loginPage.logout();
        log.info("Logout link clicked.");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Teardown
    // ──────────────────────────────────────────────────────────────────────────

    @And("close the browser")
    public void close_the_browser() {
        // Actual quit is handled by Hooks#afterScenario; nothing extra needed.
        log.info("'close the browser' step reached — browser will be closed in After hook.");
    }
}
