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
public class EnabledVerifier {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;
    private final Waiter waiter;

    public void verifyEnabled(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        verifyEnabled(locator);
    }

    public void verifyEnabled(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        verifyEnabled(by, locator.getWaitTime());
    }

    public void verifyEnabled(By by, int waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        verifyEnabled(element);
    }

    public void verifyEnabled(By by) {
        verifyEnabled(by, DEFAULT_WAIT_SECONDS);
    }

    public void verifyEnabled(WebElement element) {
        verifyEnabled(element, DEFAULT_WAIT_SECONDS);
    }

    public void verifyEnabled(WebElement element, int waitTime) {
        waiter.untilVisible(element, waitTime);
        if (!element.isEnabled()) {
            throw new AssertionError("Element is not enabled");
        }
    }
}