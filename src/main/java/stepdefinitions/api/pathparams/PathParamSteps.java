package stepdefinitions.api.pathparams;

import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class PathParamSteps {

    private final ApiSession session;

    /**
     * Sets a single path parameter with a static value.
     * <p>
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set path param "userId" as "12345"
     * }</pre>
     *
     * @param key   the path parameter name
     * @param value the path parameter value
     */
    @Given("set path param {string} as {string}")
    public void setPathParam(String key, String value) {
        session.setPathParam(key, value);
    }

    /**
     * Sets multiple path parameters at once from a DataTable.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set multiple path params
     *   | userId | 12345 |
     *   | postId | 67890 |
     * }</pre>
     *
     * @param table table containing parameter name/value pairs
     */
    @Given("set multiple path params")
    public void setPathParams(DataTable table) {
        Map<String, String> pathParams = table.asMap(String.class, String.class);
        pathParams.forEach(session::setPathParam);
    }

    /**
     * Appends a stored value to the current endpoint path.
     * Useful when a stored ID needs to be concatenated with the base path.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given append stored "userId" to endpoint
     * }</pre>
     *
     * @param storedKey the key of the stored value
     */
    @Given("add stored {string} to endpoint")
    public void addStoredToEndpoint(String storedKey) {
        String newPath = session.getBasePath() + session.getStoredValues().get(storedKey);
        session.getRequest().basePath(newPath);
    }

    /**
     * Sets a path parameter using a value previously stored in session.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set path param "id" from stored "userId"
     * }</pre>
     *
     * @param paramName the path parameter name
     * @param storedKey the session key for the stored value
     */
    @Given("set path param {string} from stored {string}")
    public void useStoredAsPathParam(String paramName, String storedKey) {
        Object parameter = session.getStoredValues().get(storedKey);
        session.setPathParam(paramName, parameter);
    }

}