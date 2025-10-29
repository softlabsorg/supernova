package domains.ui.engine.action;

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

    public void scrollTo(By by, int... waitTime) {
        waiter.untilVisible(by, waitTime);
        WebElement element = session.getDriver().findElement(by);
        scrollTo(element);
    }

    private void scrollTo(WebElement element) {
        session.getDriver().executeScript("arguments[0].scrollIntoView(true);", element);
    }
}