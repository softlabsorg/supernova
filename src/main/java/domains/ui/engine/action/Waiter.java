package domains.ui.engine.action;

import domains.ui.models.locator.Locator;
import domains.ui.session.UiSession;
import domains.ui.utils.LocatorTransformer;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class Waiter {

    public static final int DEFAULT_WAIT_SECONDS = 15;

    private final UiSession session;


    public void untilVisible(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        untilVisible(locator);
    }

    public void untilVisible(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        untilVisible(by, locator.getWaitTime());
    }

    public void untilVisible(By by, int... waitTime) {
        int secondsToWait = resolveWaitTime(waitTime);
        performWait(by, secondsToWait, ExpectedConditions::visibilityOfElementLocated);
    }

    public void untilInvisible(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        untilInvisible(locator);
    }
    public void untilInvisible(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        untilInvisible(by, locator.getWaitTime());
    }

    public void untilInvisible(By by, int... waitTime) {
        int secondsToWait = resolveWaitTime(waitTime);
        performWait(by, secondsToWait, ExpectedConditions::invisibilityOfElementLocated);
    }

    public void untilClickable(String locatorKey, int... waitTime) {
        Locator locator = session.getLocator(locatorKey);
        untilClickable(locator, waitTime);
    }

    public void untilClickable(Locator locator, int... waitTime) {
        By by = LocatorTransformer.toBy(locator);
        untilClickable(by, locator.getWaitTime());
    }

    public void untilClickable(By by, int... waitTime) {
        int secondsToWait = resolveWaitTime(waitTime);
        performWait(by, secondsToWait, ExpectedConditions::elementToBeClickable);
    }

    public void untilPresent(String locatorKey) {
        Locator locator = session.getLocator(locatorKey);
        untilPresent(locator);
    }

    public void untilPresent(Locator locator) {
        By by = LocatorTransformer.toBy(locator);
        untilPresent(by, locator.getWaitTime());
    }

    public void untilPresent(By by, int... waitTime) {
        int secondsToWait = resolveWaitTime(waitTime);
        performWait(by, secondsToWait, ExpectedConditions::presenceOfElementLocated);
    }

    public void genericWait(By by, Function<By, ExpectedCondition<?>> condition, int... waitTime) {
        int secondsToWait = resolveWaitTime(waitTime);
        performWait(by, secondsToWait, condition);
    }

    private void performWait(By by, int secondsToWait, Function<By, ExpectedCondition<?>> condition) {
        new WebDriverWait(session.getDriver(), Duration.ofSeconds(secondsToWait))
                .until(condition.apply(by));
    }

    private int resolveWaitTime(int... waitTime) {
        return (waitTime.length > 0 && waitTime[0] > 0) ? waitTime[0] : DEFAULT_WAIT_SECONDS;
    }

}