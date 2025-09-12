package domains.ui.engine.validation;

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
public class VisibilityVerifier {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;
    private final Waiter waiter;

    public void verifyVisible(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        verifyVisible(locator);
    }

    public void verifyVisible(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        verifyVisible(by, locator.getWaitTime());
    }

    public void verifyVisible(By by, int waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        verifyVisible(element);
    }

    public void verifyVisible(By by) {
        verifyVisible(by, DEFAULT_WAIT_SECONDS);
    }

    public void verifyVisible(WebElement element) {
        verifyVisible(element, DEFAULT_WAIT_SECONDS);
    }

    public void verifyVisible(WebElement element, int waitTime) {
        waiter.untilVisible(element, waitTime);
        if (!element.isDisplayed()) {
            throw new AssertionError("Element is not visible");
        }
    }
}