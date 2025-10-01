package domains.ui.engine.interaction;

import domains.ui.engine.wait.Waiter;
import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Selector {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;
    private final Waiter waiter;

    public void selectByVisibleText(String locatorKey, String text) {
        Locator locator = session.getLocator(locatorKey);
        selectByVisibleText(locator, text);
    }

    public void selectByVisibleText(Locator locator, String text) {
        By by = LocatorTransformer.toBy(locator);
        selectByVisibleText(by, text, locator.getWaitTime());
    }

    public void selectByVisibleText(By by, String text, int waitTime) {
        waiter.untilPresent(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        new Select(element).selectByVisibleText(text);
    }

    public void selectByVisibleText(By by, String text) {
        selectByVisibleText(by, text, DEFAULT_WAIT_SECONDS);
    }
}