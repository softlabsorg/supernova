package shared.listeners.testlifecycle.impl;

import domains.api.session.ApiSession;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.listeners.testlifecycle.TestLifecycleListener;

@Component
@RequiredArgsConstructor
public class ApiSessionListener implements TestLifecycleListener {

    private final ApiSession apiSession;

    @Override
    public void onScenarioStart(Scenario scenario) {
    }

    @Override
    public void onScenarioEnd(Scenario scenario) {
        apiSession.killRequest();
        apiSession.killStoredValues();
    }

}