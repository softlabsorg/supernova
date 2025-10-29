package shared.listeners.testlifecycle.impl;


import domains.ui.context.LocatorProviderContext;
import domains.ui.session.UiSession;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.listeners.testlifecycle.TestLifecycleListener;

@Component
@RequiredArgsConstructor
public class LocatorLoadListener implements TestLifecycleListener {

    private final UiSession uiSession;
    private final LocatorProviderContext locatorProviderContext;

    @Override
    public void onScenarioStart(Scenario scenario) {
        String featureName = extractFeatureName(scenario);
        uiSession.loadFeatureLocators(featureName);
        uiSession.getDriverServiceContext().initializeTagsOnce(scenario.getSourceTagNames());
    }

    @Override
    public void onScenarioEnd(Scenario scenario) {
        locatorProviderContext.reset();
    }

    private String extractFeatureName(Scenario scenario) {
        try {
            String uri = scenario.getUri().toString();
            String fileName = uri.substring(uri.lastIndexOf("/") + 1);
            return fileName.replace(".feature", "");
        } catch (Exception e) {
            System.err.println("Unable to extract feature name: " + e.getMessage());
            return "unknown-feature";
        }
    }
}
