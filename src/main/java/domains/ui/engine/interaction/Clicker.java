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
public class Clicker {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;
    private final Waiter waiter;

    public void click(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        click(locator);
    }

    public void click(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        click(by, locator.getWaitTime());
    }

    public void click(By by, int waitTime) {
        waiter.untilClickable(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        click(element);
    }

    public void click(By by) {
        click(by, DEFAULT_WAIT_SECONDS);
    }

    public void click(WebElement element) {
        click(element, DEFAULT_WAIT_SECONDS);
    }

    public void click(WebElement element, int waitTime) {
        waiter.untilClickable(element, waitTime);
        try {
            element.click();
        } catch (Exception e) {
            session.getDriver().executeScript("arguments[0].click();", element);
        }
    }
}