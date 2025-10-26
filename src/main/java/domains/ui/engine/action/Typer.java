package domains.ui.engine.action;

import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Typer {


    private final UiSession session;
    private final Waiter waiter;

    public void sendKeys(String locatorKey, String text) {
        Locator locator = session.getLocator(locatorKey);
        sendKeys(locator, text);
    }

    public void sendKeys(Locator locator, String text) {
        By by = LocatorTransformer.toBy(locator);
        sendKeys(by, text, locator.getWaitTime());
    }

    public void sendKeys(By by, String text, int waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        sendKeys(element, text);
    }

    private void sendKeys(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void sendKeysWithActions(String locatorKey, String text) {
        Locator locator = session.getLocator(locatorKey);
        sendKeysWithActions(locator, text);
    }

    public void sendKeysWithActions(Locator locator, String text) {
        By by = LocatorTransformer.toBy(locator);
        sendKeysWithActions(by, text, locator.getWaitTime());
    }

    public void sendKeysWithActions(By by, String text, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        sendKeysWithActions(element, text);
    }

    private  void sendKeysWithActions(WebElement element, String text) {
        element.clear();
        element.click();
        new Actions(session.getDriver()).sendKeys(text).perform();
    }

}