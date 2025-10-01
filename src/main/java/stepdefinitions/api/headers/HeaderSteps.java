package stepdefinitions.api.headers;

import com.github.curiousoddman.rgxgen.RgxGen;
import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;

@RequiredArgsConstructor
public class HeaderSteps {

    private final ApiSession session;

    /**
     * Sets a single request header with a static value.
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set request header "Content-Type" as "application/json"
     * Given use request header "X-Env" = "staging"
     * }</pre>
     *
     * @param headerName  the header name
     * @param headerValue the header value
     */
    @Given("set request header {string} to {string}")
    public void setHeaderWithValue(String headerName, String headerValue) {
        session.setHeader(headerName, headerValue);
    }

    /**
     * Sets multiple request headers at once from a DataTable (key/value pairs).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set multiple request headers
     *   | Authorization | Bearer 12345     |
     *   | Content-Type  | application/json |
     * }</pre>
     *
     * @param dataTable table containing header names and values
     */
    @Given("set multiple request headers")
    public void setHeadersWithValues(DataTable dataTable) {
        Map<String, String> headers = dataTable.asMap(String.class, String.class);
        headers.forEach(session::setHeader);
    }

    /**
     * Sets a request header to a Bearer token taken from stored values.
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set request header "Authorization" to bearer token from stored "authToken"
     * Given use request header "Authorization" to bearer token from stored "sessionToken"
     * }</pre>
     *
     * @param headerKey the header name (e.g., {@code Authorization})
     * @param tokenKey  the key under which the token is stored
     */
    @Given("set request header {string} to bearer token from stored {string}")
    public void setBearerTokenFromStore(String headerKey, String tokenKey) {
        String token = (String) session.getStoredValues().get(tokenKey);
        session.setHeader(headerKey, "Bearer " + token);
    }

    /**
     * Sets a request header using a system property (Java {@code -D} argument).
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set request header "X-Env" from system property "environment"
     * Given use request header "X-Trace" from system property "traceId"
     * }</pre>
     *
     * @param header the header name
     * @param env    the system property key
     */
    @Given("set request header {string} from system property {string}")
    public void setHeaderFromEnv(String header, String env) {
        session.setHeader(header, System.getProperty(env));
    }

    /**
     * Sets a request header with a random alphanumeric string of the given length.
     * The generated value is also stored in the session under the header name.
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set request header "X-Request-Id" to random string of length 12
     * Given use request header "X-Seed" to random string of length 8
     * }</pre>
     *
     * @param headerName the header name
     * @param length     number of characters to generate
     */
    @Given("set request header {string} to random string of length {int}")
    public void setHeaderRandomLength(String headerName, int length) {
        String randomValue = RandomStringUtils.randomAlphanumeric(length);
        session.getStoredValues().put(headerName, randomValue);
        session.setHeader(headerName, randomValue);
    }

    /**
     * Sets a request header with a random value generated from a regex pattern.
     * The generated value is also stored in the session under the header name.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set request header "X-UserId" to random value matching "[A-Z]{3}[0-9]{4}"
     * }</pre>
     *
     * @param key   the header name
     * @param regex regex pattern for random value generation
     */
    @Given("set request header {string} to random value matching {string}")
    public void setRegexValue(String key, String regex) {
        String randomValue = RgxGen.parse(regex).generate();
        session.getStoredValues().put(key, randomValue);
        session.setHeader(key, randomValue);
    }

    /**
     * Stores the value of a request header into session storage.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given store request header "X-Trace-Id" as "traceId"
     * }</pre>
     *
     * @param headerName the name of the request header to read
     * @param storeKey   the key under which the value will be stored
     */
    @Given("store request header {string} as {string}")
    public void storeHeader(String headerName, String storeKey) {
        String headerValue = (String) session.getHeaders().get(headerName);
        session.setValue(storeKey, headerValue);
    }

    /**
     * Sets a request header from a globally stored value (shared across scenarios).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set request header "X-Api-Key" as global "adminApiKey"
     * }</pre>
     *
     * @param headerKey the header name
     * @param globalKey the global storage key
     */
    @Given("set request header {string} as global {string}")
    public void useGlobalValueAsHeader(String headerKey, String globalKey) {
        Object value = session.getGlobalValue(globalKey);
        session.setHeader(headerKey, value);
    }

    /**
     * Sets the Authorization header with a Bearer token value retrieved from session storage.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set authorization header from stored "userToken"
     * }</pre>
     *
     * @param storedKey the session storage key containing the token
     */
    @Given("set authorization header from stored {string}")
    public void useStoredAsAuthHeader(String storedKey) {
        Object value = session.getValue(storedKey);
        session.addAccessTokenToRequest(value.toString());
    }

    /**
     * Sets a request header using a value retrieved from session storage.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set request header "X-Correlation-Id" as stored "correlationId"
     * }</pre>
     *
     * @param headerName the header name
     * @param storedKey  the session storage key containing the value
     */
    @Given("set request header {string} as stored {string}")
    public void useStoredValueAsHeader(String headerName, String storedKey) {
        Object value = session.getValue(storedKey);
        session.setHeader(headerName, value);
    }

}