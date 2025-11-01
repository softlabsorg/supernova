package domains.ui.engine.action;

import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Finder {

    private final Waiter waiter;
    private final UiSession session;

    public WebElement findElement(String key) {
        return findElement(session.getLocator(key));
    }

    public WebElement findElement(Locator locator) {
        return findElement(LocatorTransformer.toBy(locator), locator.getWaitTime());
    }

    public WebElement findElement(By locator, int... waitTime) {
        waiter.untilVisible(locator, waitTime);
        return session.getDriver().findElement(locator);
    }

    public List<WebElement> findElements(String key) {
        return findElements(session.getLocator(key));
    }

    public List<WebElement> findElements(Locator locator) {
        return findElements(LocatorTransformer.toBy(locator), locator.getWaitTime());
    }

    public List<WebElement> findElements(By locator, int... waitTime) {
        waiter.untilVisible(locator, waitTime);
        return session.getDriver().findElements(locator);
    }

    public By findBy(String key) {
        Locator locator = session.getLocator(key);
        return LocatorTransformer.toBy(locator);
    }

}
