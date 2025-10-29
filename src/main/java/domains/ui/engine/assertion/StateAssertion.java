package domains.ui.engine.assertion;

import domains.ui.engine.action.Waiter;
import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.testng.Assert;

@Component
@RequiredArgsConstructor
public class StateAssertion {

    private final UiSession session;
    private final Waiter waiter;

    public void assertVisible(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        assertVisible(locator);
    }

    public void assertVisible(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        assertVisible(by, locator.getWaitTime());
    }

    public void assertVisible(By by, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertVisible(element);
    }

    public void assertVisible(WebElement element) {
        Assert.assertTrue(element.isDisplayed(), "Element is not visible");
    }

    public void assertNotVisible(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        assertNotVisible(locator);
    }

    public void assertNotVisible(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        assertNotVisible(by, locator.getWaitTime());
    }

    public void assertNotVisible(By by, int... waitTime) {
        waiter.untilInvisible(by, waitTime);
    }

    // ========== ENABLED/DISABLED ASSERTIONS ==========

    public void assertEnabled(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        assertEnabled(locator);
    }

    public void assertEnabled(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        assertEnabled(by, locator.getWaitTime());
    }

    public void assertEnabled(By by, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertEnabled(element);
    }

    public void assertEnabled(WebElement element) {
        Assert.assertTrue(element.isEnabled(), "Element is not enabled");
    }

    public void assertDisabled(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        assertDisabled(locator);
    }

    public void assertDisabled(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        assertDisabled(by, locator.getWaitTime());
    }

    public void assertDisabled(By by, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertDisabled(element);
    }

    public void assertDisabled(WebElement element) {
        Assert.assertFalse(element.isEnabled(), "Element is not disabled");
    }

}
