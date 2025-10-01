package domains.ui.engine.interaction;

import domains.ui.engine.wait.Waiter;
import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Typer {

    private static final int DEFAULT_WAIT_SECONDS = 10;

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

    public void sendKeys(By by, String text) {
        sendKeys(by, text, DEFAULT_WAIT_SECONDS);
    }

    public void sendKeys(WebElement element, String text) {
        sendKeys(element, text, DEFAULT_WAIT_SECONDS);
    }

    public void sendKeys(WebElement element, String text, int waitTime) {
        waiter.untilVisible(element, waitTime);
        element.clear();
        element.sendKeys(text);
    }
}