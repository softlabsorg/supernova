package shared.listeners.testlifecycle;

import io.cucumber.java.Scenario;

public interface TestLifecycleListener {
    void onScenarioStart(Scenario scenario);
    void onScenarioEnd(Scenario scenario);
}