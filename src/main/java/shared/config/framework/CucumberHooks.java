package shared.config.framework;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import shared.listeners.testlifecycle.TestLifecycleListener;

import java.util.List;

@RequiredArgsConstructor
public class CucumberHooks {

    private final List<TestLifecycleListener> listeners;

    @Before
    public void before(Scenario scenario) {
        listeners.forEach(listener -> listener.onScenarioStart(scenario));
    }

    @After
    public void after(Scenario scenario) {
        listeners.forEach(listener -> listener.onScenarioEnd(scenario));
    }

}