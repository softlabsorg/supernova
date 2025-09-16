package shared.utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.restassured.response.Response;

public class LoggingToExtentReport {

    public static void logResponse(Response response) {
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,
                "<textarea readonly>"
                        + response.getBody().asPrettyString()
                        + "</textarea>");
    }

    public static void logSentText(String text) {
        ExtentCucumberAdapter.getCurrentStep().log(Status.INFO,
                "<textarea readonly>"
                        + text
                        + "</textarea>");
    }
}
