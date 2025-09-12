package domains.ui.initializers;

import domains.ui.utils.PlatformIdentifier;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;
import shared.config.application.models.GlobalConfiguration;
import shared.exception.ConfigurationParsingException;
import shared.utils.ApplicationConfigProvider;

import java.util.*;

@Data
@Component
@RequiredArgsConstructor
public class DriverInitializer {

    private final PlatformIdentifier platformIdentifier;
    private final ApplicationConfigProvider configProvider;

    public RemoteWebDriver initializeDriver(Collection<String> tags) {
        String platform = resolvePlatform(tags);
        return createDriver(platform);
    }

    private String resolvePlatform(Collection<String> tags) {
        String tagPlatform = platformIdentifier.identify(tags);
        return tagPlatform != null ? tagPlatform : resolveFromConfig();
    }

    private String resolveFromConfig() {
        Map<String, Object> configuredPlatforms = collectConfiguredPlatforms();
        List<String> availablePlatforms = new ArrayList<>(configuredPlatforms.keySet());

        return resolvePlatformFromList(availablePlatforms);
    }

    private Map<String, Object> collectConfiguredPlatforms() {
        GlobalConfiguration config = configProvider.get();
        Map<String, Object> platforms = new LinkedHashMap<>();

        if (config.getWeb() != null) platforms.put("web", config.getWeb());
        if (config.getAndroid() != null) platforms.put("android", config.getAndroid());
        if (config.getIos() != null) platforms.put("ios", config.getIos());

        return platforms;
    }

    private String resolvePlatformFromList(List<String> availablePlatforms) {
        return switch (availablePlatforms.size()) {
            case 1 -> availablePlatforms.getFirst();
            case 0 -> throw new ConfigurationParsingException(
                    "UI test identified, but no valid UI configuration (web/android) found.");
            default -> throw new IllegalStateException(
                    "Multiple UI platforms found. Use @web, @android, or @ios tag.");
        };
    }


    private RemoteWebDriver createDriver(String platform) {
        return switch (platform) {
            case "web" -> initializeWebDriver();
            case "android" -> initializeAndroidDriver();
            case "ios" -> initializeIOSDriver();
            default -> throw new IllegalArgumentException("Unsupported platform: " + platform);
        };
    }

    private RemoteWebDriver initializeWebDriver() {
        return Optional.ofNullable(configProvider.get().getWeb())
                .map(WebDriverInitializer::initializeWebDriver)
                .orElseThrow(() -> new ConfigurationParsingException("Missing 'web' section in configuration."));
    }

    private RemoteWebDriver initializeAndroidDriver() {
        return Optional.ofNullable(configProvider.get().getAndroid())
                .map(AndroidDriverInitializer::initializeAndroidDriver)
                .orElseThrow(() -> new ConfigurationParsingException("Missing 'android' section in configuration."));
    }

    private RemoteWebDriver initializeIOSDriver() {
        return Optional.ofNullable(configProvider.get().getIos())
                .map(IOSDriverInitializer::initializeIOSDriver)
                .orElseThrow(() -> new ConfigurationParsingException("Missing 'ios' section in configuration."));
    }


}