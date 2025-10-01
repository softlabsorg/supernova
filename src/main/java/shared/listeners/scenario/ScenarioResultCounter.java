package shared.listeners.scenario;

import lombok.Getter;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScenarioResultCounter implements ITestListener {

    @Getter
    private static int passedCount = 0;
    @Getter
    private static int failedCount = 0;

    @Override
    public void onTestSuccess(ITestResult result) {
        passedCount++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failedCount++;
    }

}