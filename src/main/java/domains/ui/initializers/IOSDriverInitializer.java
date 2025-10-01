package domains.ui.initializers;

import domains.ui.models.config.IOS;
import io.appium.java_client.ios.IOSDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

public class IOSDriverInitializer {

    public static IOSDriver initializeIOSDriver(IOS iosConfig) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:app", getAppAbsolutePath(iosConfig.getAppName()));
        capabilities.setCapability("appium:bundleId", iosConfig.getBundleId());
        return createIOSDriver(capabilities);
    }

    @SneakyThrows
    private static IOSDriver createIOSDriver(DesiredCapabilities capabilities) {
        return new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    @SneakyThrows
    private static String getAppAbsolutePath(String appFileName) {
        URL resource = IOSDriverInitializer.class
                .getClassLoader()
                .getResource("app/" + appFileName);
        if (resource == null) {
            throw new FileNotFoundException("iOS app file not found in classpath: app/" + appFileName);
        }
        return Paths.get(resource.toURI()).toFile().getAbsolutePath();
    }

}