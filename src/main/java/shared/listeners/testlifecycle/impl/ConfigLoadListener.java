package shared.listeners.testlifecycle.impl;

import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.listeners.testlifecycle.TestLifecycleListener;
import shared.utils.ApplicationConfigProvider;

@Component
@RequiredArgsConstructor
public class ConfigLoadListener implements TestLifecycleListener {

    private final ApplicationConfigProvider configProvider;

    @Override
    public void onScenarioStart(Scenario scenario) {
        configProvider.get();
    }

    @Override
    public void onScenarioEnd(Scenario scenario) {

    }

}