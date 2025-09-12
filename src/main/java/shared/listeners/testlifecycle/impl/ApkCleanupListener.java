package shared.listeners.testlifecycle.impl;

import domains.ui.session.UiSession;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.listeners.testlifecycle.TestLifecycleListener;

@Component
@RequiredArgsConstructor
public class ApkCleanupListener implements TestLifecycleListener {

    private final UiSession uiSession;

    @Override
    public void onScenarioStart(Scenario scenario) {
    }

    @Override
    public void onScenarioEnd(Scenario scenario) {
        uiSession.deleteApkIfDownloaded();
    }
}
