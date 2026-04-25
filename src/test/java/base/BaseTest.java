package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * BaseTest — manages WebDriver lifecycle, explicit waits, and screenshots.
 * All step definitions and hooks inject this via PicoContainer.
 */
public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    /**
     * Launch the browser specified in config.properties (default: chrome).
     * Supports headless mode for CI environments.
     */
    public void initDriver() {
        String browser = ConfigReader.get("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));

        WebDriver driver;
        switch (browser) {
            case "firefox" -> {
                FirefoxOptions ffOpts = new FirefoxOptions();
                if (headless) ffOpts.addArguments("--headless");
                driver = new FirefoxDriver(ffOpts);
                log.info("Firefox driver initialised (headless={})", headless);
            }
            default -> {
                ChromeOptions chromeOpts = new ChromeOptions();
                if (headless) {
                    chromeOpts.addArguments("--headless=new", "--no-sandbox",
                            "--disable-dev-shm-usage", "--window-size=1920,1080");
                }
                chromeOpts.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOpts);
                log.info("Chrome driver initialised (headless={})", headless);
            }
        }

        long implicitWait = Long.parseLong(ConfigReader.get("implicit.wait.seconds", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().window().maximize();
        driverThread.set(driver);
    }

    /** Returns the WebDriver for the current thread. */
    public WebDriver getDriver() {
        return driverThread.get();
    }

    /**
     * Returns a pre-configured WebDriverWait using the configured explicit timeout.
     */
    public WebDriverWait getWait() {
        long explicitWait = Long.parseLong(ConfigReader.get("explicit.wait.seconds", "15"));
        return new WebDriverWait(getDriver(), Duration.ofSeconds(explicitWait));
    }

    /**
     * Captures a PNG screenshot and saves it to target/screenshots/<timestamp>.png.
     *
     * @return absolute path of the saved file, or empty string on failure
     */
    public String captureScreenshot(String scenarioName) {
        try {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String path = "target/screenshots/" + safeName + "_" + timestamp + ".png";
            File dest = new File(path);
            FileUtils.copyFile(
                    ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE),
                    dest);
            log.info("Screenshot saved → {}", path);
            return dest.getAbsolutePath();
        } catch (IOException e) {
            log.error("Screenshot failed: {}", e.getMessage());
            return "";
        }
    }

    /** Quits the driver and removes it from ThreadLocal. */
    public void tearDown() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
            log.info("Browser closed.");
        }
    }
}
