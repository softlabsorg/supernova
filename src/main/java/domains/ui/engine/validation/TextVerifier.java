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
public class TextVerifier {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;
    private final Waiter waiter;

    public void verifyText(String locatorKey, String expectedText) {
        Locator locator = session.getLocator(locatorKey);
        verifyText(locator, expectedText);
    }

    public void verifyText(Locator locator, String expectedText) {
        By by = LocatorTransformer.toBy(locator);
        verifyText(by, expectedText, locator.getWaitTime());
    }

    public void verifyText(By by, String expectedText, int waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        verifyText(element, expectedText);
    }

    public void verifyText(By by, String expectedText) {
        verifyText(by, expectedText, DEFAULT_WAIT_SECONDS);
    }

    public void verifyText(WebElement element, String expectedText) {
        verifyText(element, expectedText, DEFAULT_WAIT_SECONDS);
    }

    public void verifyText(WebElement element, String expectedText, int waitTime) {
        waiter.untilVisible(element, waitTime);
        String actual = element.getText();
        if (!actual.trim().equals(expectedText.trim())) {
            throw new AssertionError("Expected: '" + expectedText + "', but got: '" + actual + "'");
        }
    }
}