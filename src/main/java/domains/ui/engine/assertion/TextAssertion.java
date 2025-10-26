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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

@Component
@RequiredArgsConstructor
public class TextAssertion {

    private final UiSession session;
    private final Waiter waiter;

    public void assertTextEquals(String locatorKey, String expectedText) {
        Locator locator = session.getLocator(locatorKey);
        assertTextEquals(locator, expectedText);
    }

    public void assertTextEquals(Locator locator, String expectedText) {
        By by = LocatorTransformer.toBy(locator);
        assertTextEquals(by, expectedText, locator.getWaitTime());
    }

    public void assertTextEquals(By by, String expectedText, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertTextEquals(element, expectedText);
    }

    public void assertTextEquals(WebElement element, String expectedText) {
        Assert.assertEquals(element.getText(), expectedText,
                "Element text does not match expected value");
    }

    public void assertTextNotEquals(String locatorKey, String unexpectedText) {
        Locator locator = session.getLocator(locatorKey);
        assertTextNotEquals(locator, unexpectedText);
    }

    public void assertTextNotEquals(Locator locator, String unexpectedText) {
        By by = LocatorTransformer.toBy(locator);
        assertTextNotEquals(by, unexpectedText, locator.getWaitTime());
    }

    public void assertTextNotEquals(By by, String unexpectedText, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertTextNotEquals(element, unexpectedText);
    }

    public void assertTextNotEquals(WebElement element, String unexpectedText) {
        Assert.assertNotEquals(element.getText(), unexpectedText,
                "Element text should not equal the unexpected value");
    }

    // ========== TEXT CONTAINS ASSERTIONS ==========

    public void assertTextContains(String locatorKey, String expectedText) {
        Locator locator = session.getLocator(locatorKey);
        assertTextContains(locator, expectedText);
    }

    public void assertTextContains(Locator locator, String expectedText) {
        By by = LocatorTransformer.toBy(locator);
        assertTextContains(by, expectedText, locator.getWaitTime());
    }

    public void assertTextContains(By by, String expectedText, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertTextContains(element, expectedText);
    }

    public void assertTextContains(WebElement element, String expectedText) {
        assertThat("Element text should contain expected substring",
                element.getText(), containsString(expectedText));
    }

    public void assertTextNotContains(String locatorKey, String unexpectedText) {
        Locator locator = session.getLocator(locatorKey);
        assertTextNotContains(locator, unexpectedText);
    }

    public void assertTextNotContains(Locator locator, String unexpectedText) {
        By by = LocatorTransformer.toBy(locator);
        assertTextNotContains(by, unexpectedText, locator.getWaitTime());
    }

    public void assertTextNotContains(By by, String unexpectedText, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        assertTextNotContains(element, unexpectedText);
    }

    public void assertTextNotContains(WebElement element, String unexpectedText) {
        assertThat("Element text should not contain unexpected substring",
                element.getText(), not(containsString(unexpectedText)));
    }

}