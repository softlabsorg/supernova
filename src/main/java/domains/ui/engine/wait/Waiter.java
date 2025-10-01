package domains.ui.engine.wait;

import domains.ui.session.UiSession;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class Waiter {

    private static final int DEFAULT_WAIT_SECONDS = 10;

    private final UiSession session;

    public void untilVisible(By by) {
        untilVisible(by, DEFAULT_WAIT_SECONDS);
    }

    public void untilVisible(By by, int waitTime) {
        new WebDriverWait(session.getDriver(), Duration.ofSeconds(waitTime))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void untilClickable(By by) {
        untilClickable(by, DEFAULT_WAIT_SECONDS);
    }

    public void untilClickable(By by, int waitTime) {
        new WebDriverWait(session.getDriver(), Duration.ofSeconds(waitTime))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public void untilPresent(By by) {
        untilPresent(by, DEFAULT_WAIT_SECONDS);
    }

    public void untilPresent(By by, int waitTime) {
        new WebDriverWait(session.getDriver(), Duration.ofSeconds(waitTime))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void untilVisible(WebElement element, int waitTime) {
        new WebDriverWait(session.getDriver(), Duration.ofSeconds(waitTime))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void untilClickable(WebElement element, int waitTime) {
        new WebDriverWait(session.getDriver(), Duration.ofSeconds(waitTime))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

}