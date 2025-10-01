package shared.listeners.testlifecycle.impl;

import domains.ui.session.UiSession;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.listeners.testlifecycle.TestLifecycleListener;

@Component
@RequiredArgsConstructor
public class UiDriverListener implements TestLifecycleListener {

    private final UiSession uiSession;

    @Override
    public void onScenarioStart(Scenario scenario) {
        if (isAndroidTest(scenario)) {
            uiSession.getFirebaseApkDownloader().downloadApkIfNeeded();
        }

        if (isUiTest(scenario)) {
            uiSession.getDriver();
        }
    }

    @Override
    public void onScenarioEnd(Scenario scenario) {
        if (isUiTest(scenario)) {
            uiSession.reset();
        }
    }

    private boolean isUiTest(Scenario scenario) {
        return scenario.getSourceTagNames().stream().anyMatch(tag ->
                tag.equalsIgnoreCase("@web") ||
                        tag.equalsIgnoreCase("@android") ||
                        tag.equalsIgnoreCase("@ios") ||
                        tag.equalsIgnoreCase("@desktop")
        );
    }

    private boolean isAndroidTest(Scenario scenario) {
        return scenario.getSourceTagNames().stream().anyMatch(tag ->
                tag.equalsIgnoreCase("@android")
        );
    }
}
