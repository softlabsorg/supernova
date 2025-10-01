package stepdefinitions.api.queryparams;

import com.github.curiousoddman.rgxgen.RgxGen;
import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;

@RequiredArgsConstructor
public class QueryParamsSteps {

    private final ApiSession session;

    /**
     * Sets a single query parameter with a static value.
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set query param "page" as "1"
     * }</pre>
     *
     * @param paramName  query parameter name
     * @param paramValue query parameter value
     */
    @Given("set query param {string} as {string}")
    public void setSingleQueryParam(String paramName, String paramValue) {
        session.setRequestParam(paramName, paramValue);
    }

    /**
     * Sets multiple query parameters at once from a DataTable.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set multiple query params
     *   | page | 1   |
     *   | size | 50  |
     * }</pre>
     *
     * @param dataTable table containing name/value pairs
     */
    @Given("set multiple query params")
    public void setQueryParams(DataTable dataTable) {
        Map<String, String> params = dataTable.asMap(String.class, String.class);
        params.forEach(session::setRequestParam);
    }

    /**
     * Stores a query parameter value from the current request into session storage.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given store query param "page" as "storedPage"
     * }</pre>
     *
     * @param paramName query parameter name to read
     * @param storeKey  key to store under in session
     */
    @Given("store query param {string} as {string}")
    public void storeQueryParamValue(String paramName, String storeKey) {
        Map<String, Object> params = session.getRequestParams();
        Object value = params.get(paramName);
        session.setValue(storeKey, value);
    }

    /**
     * Sets a query parameter to a random alphanumeric string of the given length.
     * Also stores the generated value under the parameter name.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set query param "requestId" to random string of length 12
     * }</pre>
     *
     * @param name   query parameter name
     * @param length number of characters to generate
     */
    @Given("set query param {string} to random string of length {int}")
    public void setQueryParamToRandomLengthValue(String name, int length) {
        String randomValue = RandomStringUtils.randomAlphanumeric(length);
        session.getStoredValues().put(name, randomValue);
        session.setRequestParam(name, randomValue);
    }

    /**
     * Sets a query parameter to a random value matching a regex pattern.
     * Also stores the generated value under the parameter name.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set query param "code" to random value matching "[A-Z]{3}[0-9]{4}"
     * }</pre>
     *
     * @param name  query parameter name
     * @param regex pattern for random generation
     */
    @Given("set query param {string} to random value matching {string}")
    public void setQueryParamToRandomRegexValue(String name, String regex) {
        String value = RgxGen.parse(regex).generate();
        session.getStoredValues().put(name, value);
        session.setRequestParam(name, value);
    }

    /**
     * Uses a globally stored value as a query parameter.
     * The value is retrieved from global storage (shared across all scenarios).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given use global "adminApiKey" as query param "apiKey"
     * }</pre>
     *
     * @param globalKey the key in global storage
     * @param paramName the query parameter name to set
     */
    @Given("use global {string} as query param {string}")
    public void useGlobalValueAsQueryParam(String globalKey, String paramName) {
        Object value = session.getGlobalValue(globalKey);
        session.setRequestParam(paramName, value);
    }

    /**
     * Uses a value from session storage as a query parameter.
     * The value is retrieved from the current test session's storage.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given use stored "createdUserId" as query param "userId"
     * }</pre>
     *
     * @param storedKey the key in session storage
     * @param paramName the query parameter name to set
     */
    @Given("use stored {string} as query param {string}")
    public void useSessionValueAsQueryParam(String storedKey, String paramName) {
        Object value = session.getValue(storedKey);
        session.setRequestParam(paramName, value);
    }

}