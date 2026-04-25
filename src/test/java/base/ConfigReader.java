package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader — loads config.properties from the classpath once and caches it.
 * Usage: ConfigReader.get("base.url")
 */
public class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final Properties PROPS = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        try (InputStream is = ConfigReader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new RuntimeException(CONFIG_FILE + " not found on classpath");
            }
            PROPS.load(is);
            log.info("Config loaded from {}", CONFIG_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + CONFIG_FILE, e);
        }
    }

    private ConfigReader() {}

    /**
     * Returns the property value, falling back to the provided default.
     * System properties override file values (useful for CI -D overrides).
     */
    public static String get(String key, String defaultValue) {
        // CI/CD pipeline can override via -Dkey=value
        String sysProp = System.getProperty(key);
        if (sysProp != null && !sysProp.isBlank()) return sysProp;
        return PROPS.getProperty(key, defaultValue);
    }

    public static String get(String key) {
        return get(key, "");
    }
}
