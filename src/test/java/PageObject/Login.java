package PageObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Login — Page Object for the NopCommerce admin login page.
 * Uses Selenium PageFactory for element initialisation.
 */
public class Login {

    private static final Logger log = LogManager.getLogger(Login.class);

    private final WebDriver driver;

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.debug("Login Page Object initialised.");
    }

    // ── Locators ──────────────────────────────────────────────────────────────

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class='button-1 login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    private WebElement logoutLink;

    // ── Actions ───────────────────────────────────────────────────────────────

    public void enteremail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        log.debug("Email entered: {}", email);
    }

    public void password(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        log.debug("Password entered (masked).");
    }

    public void submit() {
        loginButton.click();
        log.debug("Login button clicked.");
    }

    public void logout() {
        logoutLink.click();
        log.debug("Logout link clicked.");
    }
}
