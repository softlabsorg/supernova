package domains.ui.initializers;

import domains.ui.models.config.Web;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.UUID;

public class WebDriverInitializer {

    public static RemoteWebDriver initializeWebDriver(Web webConfig) {
        RemoteWebDriver driver = new ChromeDriver(createChromeOptions(webConfig));
        configureTimeouts(driver, webConfig);
        clearCookiesIfNeeded(driver, webConfig);
        configureWindowSize(driver, webConfig);
        return driver;
    }


    private static ChromeOptions createChromeOptions(Web config) {
        ChromeOptions options = new ChromeOptions();

        if (!config.isShowBrowser()) {
            options.addArguments("--headless");
        }

        if (config.isDisableNotifications()) {
            options.addArguments("--disable-notifications");
        }

        if (config.isDisablePopupBlocking()) {
            options.addArguments("--disable-popup-blocking");
        }

        if (config.isDisableGpu()) {
            options.addArguments("--disable-gpu");
        }

        if (!config.isMaximize()) {
            options.addArguments("--window-size=1920,1080");
        }

        if (config.isClearBrowserCache()) {
            String tmpProfile = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + UUID.randomUUID();
            options.addArguments("--user-data-dir=" + tmpProfile);
        }

        return options;
    }


    private static void configureTimeouts(RemoteWebDriver driver, Web config) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(config.getScriptTimeout()));
    }

    private static void clearCookiesIfNeeded(RemoteWebDriver driver, Web config) {
        if (config.isDeleteAllCookies()) {
            driver.manage().deleteAllCookies();
        }
    }

    private static void configureWindowSize(RemoteWebDriver driver, Web config) {
        if (config.isMaximize()) {
            driver.manage().window().maximize();
        } else if (config.getWindowSize() != null && !config.getWindowSize().isBlank()) {
            String[] size = config.getWindowSize().split(",");
            int width = Integer.parseInt(size[0].trim());
            int height = Integer.parseInt(size[1].trim());
            driver.manage().window().setSize(new Dimension(width, height));
        }
    }

}