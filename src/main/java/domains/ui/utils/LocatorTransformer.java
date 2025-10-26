package domains.ui.utils;

import domains.ui.models.locator.Locator;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LocatorTransformer {

    public static By toBy(Locator locator) {
        String type = locator.getType().toLowerCase();
        String value = locator.getValue();

        return switch (type) {
            case "xpath" -> By.xpath(value);
            case "css" -> By.cssSelector(value);
            case "id" -> By.id(value);
            case "name" -> By.name(value);
            case "class" -> By.className(value);
            case "tag" -> By.tagName(value);
            case "accessibility-id" -> AppiumBy.accessibilityId(value);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + type);
        };
    }

}