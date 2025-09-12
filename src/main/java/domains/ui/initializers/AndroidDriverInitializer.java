package domains.ui.initializers;

import domains.ui.models.config.Android;
import io.appium.java_client.android.AndroidDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;

public class AndroidDriverInitializer {

    public static AndroidDriver initializeAndroidDriver(Android androidConfig) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:app", getApkAbsolutePath(androidConfig.getAppName()));
        capabilities.setCapability("appium:appPackage", androidConfig.getAppPackage());
        capabilities.setCapability("appium:appActivity", androidConfig.getAppActivity());
        return createAndroidDriver(capabilities);
    }

    @SneakyThrows
    private static AndroidDriver createAndroidDriver(DesiredCapabilities capabilities) {
        return new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    @SneakyThrows
    private static String getApkAbsolutePath(String apkFileName) {
        URL resource = AndroidDriverInitializer.class
                .getClassLoader()
                .getResource("apk/" + apkFileName);
        if (resource == null) {
            throw new FileNotFoundException("APK file not found in classpath: apk/" + apkFileName);
        }
        return Paths.get(resource.toURI()).toFile().getAbsolutePath();
    }

}