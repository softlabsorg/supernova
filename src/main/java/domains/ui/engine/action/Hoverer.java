package domains.ui.engine.action;

import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Hoverer {


    private final UiSession session;
    private final Waiter waiter;

    public void hover(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        hover(locator);
    }

    public void hover(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        hover(by, locator.getWaitTime());
    }

    public void hover(By by, int... waitTime) {
        waiter.untilVisible(by);
        WebElement element = session.getDriver().findElement(by);
        hover(element);
    }

    public void hover(WebElement element) {
        new Actions(session.getDriver()).moveToElement(element).perform();
    }
}