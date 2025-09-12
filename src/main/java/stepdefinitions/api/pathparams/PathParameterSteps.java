package stepdefinitions.api.pathparams;

import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class PathParameterSteps {

    private final ApiSession session;

    @Given("Set path param {string} as {string}")
    public void setPathParam(String key, String value) {
        session.setPathParam(key, value);
    }

    @Given("Set path params")
    public void setPathParams(DataTable dataTable) {
        Map<String, String> pathParams = dataTable.asMap(String.class, String.class);
        pathParams.forEach(session::setPathParam);
    }

    @Given("Use stored {string} as path param {string}")
    public void useStoredValueAsPathParam(String storedKey, String paramName) {
        Object parameter = session.getStoredValues().get(storedKey);
        session.setPathParam(paramName, parameter);
    }

    @Given("Append stored {string} to endpoint")
    public void appendStoredValueToEndpoint(String storedKey) {
        String newPath = session.getBasePath() + session.getStoredValues().get(storedKey);
        session.getRequest().basePath(newPath);
    }
}
