package domains.ui.session;

import domains.ui.models.locator.Locator;
import domains.ui.context.DriverServiceContext;
import domains.ui.context.LocatorProviderContext;
import domains.ui.utils.PlatformIdentifier;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import shared.utils.ApplicationConfigProvider;
import shared.utils.FirebaseApkDownloader;

@Data
@Component
@RequiredArgsConstructor
public class UiSession {

    private final DriverServiceContext driverServiceContext;
    private final LocatorProviderContext locatorProviderContext;
    private final ApplicationConfigProvider applicationConfigProvider;
    private final PlatformIdentifier platformIdentifier;
    private final FirebaseApkDownloader firebaseApkDownloader;

    public RemoteWebDriver getDriver() {
        return driverServiceContext.getDriver();
    }

    public Locator getLocator(String key) {
        return locatorProviderContext.getLocator(key);
    }

    public void reset() {
        locatorProviderContext.reset();
        driverServiceContext.quitDriver();
    }

    public void loadFeatureLocators(String scenarioName) {
        locatorProviderContext.loadFeatureLocators(scenarioName);
    }

    public void deleteApkIfDownloaded() {
        firebaseApkDownloader.deleteDownloadedApkIfExists();
    }


}