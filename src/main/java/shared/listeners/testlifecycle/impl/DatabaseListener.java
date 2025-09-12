package shared.listeners.testlifecycle.impl;

import domains.database.session.DatabaseSession;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.listeners.testlifecycle.TestLifecycleListener;

@Component
@RequiredArgsConstructor
public class DatabaseListener implements TestLifecycleListener {

    private final DatabaseSession databaseSession;

    private String lastFeatureName = null;

    @Override
    public void onScenarioStart(Scenario scenario) {
        databaseSession.connectIfNeeded();
    }

    @Override
    public void onScenarioEnd(Scenario scenario) {
        String currentFeatureName = getFeatureName(scenario);

        if (lastFeatureName != null && !lastFeatureName.equals(currentFeatureName)) {
            closeAtEndOfFeature();
        }

        lastFeatureName = currentFeatureName;
    }

    private String getFeatureName(Scenario scenario) {
        String uri = scenario.getUri().toString();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    private void closeAtEndOfFeature() {
        databaseSession.closeAllConnections();
    }
}