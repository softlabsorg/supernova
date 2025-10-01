package domains.ui.session;

import domains.ui.initializers.DriverInitializer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import shared.exception.ConfigurationParsingException;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class DriverServiceContext {

    private final ThreadLocal<RemoteWebDriver> driverThreadLocal = ThreadLocal.withInitial(() -> null);
    private final DriverInitializer driverInitializer;
    private Collection<String> tags;

    public void initializeTagsOnce(Collection<String> tags) {
        if (this.tags != null) return;
        this.tags = tags;
    }

    public RemoteWebDriver getDriver() {
        initializeDriverIfAbsent();
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        quitIfDriverExists();
        validateConfigOnQuit();
    }

    private void initializeDriverIfAbsent() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(driverInitializer.initializeDriver(tags));
        }
    }

    private void quitIfDriverExists() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    private void validateConfigOnQuit() {
        boolean noUiConfig = driverInitializer.getConfigProvider().get().getWeb() == null && driverInitializer.getConfigProvider().get().getAndroid() == null;

        if (noUiConfig) {
            throw new ConfigurationParsingException(
                    "UI test identified, but configuration file is missing or incomplete."
            );
        }
    }

}