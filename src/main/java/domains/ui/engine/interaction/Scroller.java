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
public class Scroller {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;
    private final Waiter waiter;

    public void scrollTo(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        scrollTo(locator);
    }

    public void scrollTo(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        scrollTo(by, locator.getWaitTime());
    }

    public void scrollTo(By by, int waitTime) {
        waiter.untilPresent(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        scrollTo(element);
    }

    public void scrollTo(By by) {
        scrollTo(by, DEFAULT_WAIT_SECONDS);
    }

    public void scrollTo(WebElement element) {
        session.getDriver().executeScript("arguments[0].scrollIntoView(true);", element);
    }
}