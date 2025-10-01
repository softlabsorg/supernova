package stepdefinitions.api.queryparams;

import com.github.curiousoddman.rgxgen.RgxGen;
import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Map;

@RequiredArgsConstructor
public class QueryParameterSteps {

    private final ApiSession session;

    @Given("Set query params")
    public void setQueryParams(DataTable dataTable) {
        Map<String, String> params = dataTable.asMap(String.class, String.class);
        params.forEach(session::setRequestParam);
    }

    @Given("Set query param {string} as {string}")
    public void setQueryParam(String paramName, String paramValue) {
        session.setRequestParam(paramName, paramValue);
    }

    @Given("Use stored {string} as query param {string}")
    public void useStoredValueAsQueryParam(String storedKey, String paramName) {
        Object value = session.getStoredValues().get(storedKey);
        session.setRequestParam(paramName, value);
    }

    @Given("Use global value {string} as query param {string}")
    public void useGlobalValueAsQueryParam(String globalKey, String paramName) {
        Object value = session.getGlobalValue(globalKey);
        session.setRequestParam(paramName, value);
    }

    @Given("Use session stored {string} as query param {string}")
    public void useSessionStoredValueAsQueryParam(String sessionKey, String queryParam) {
        Object value = session.getValue(sessionKey);
        session.setRequestParam(queryParam, value);
    }

    @Given("Set query param {string} to random value of length {int}")
    public void setRandomValueInQueryParam(String paramName, int length) {
        String randomValue = RandomStringUtils.randomAlphanumeric(length);
        session.getStoredValues().put(paramName, randomValue);
        session.setRequestParam(paramName, randomValue);
    }

    @Given("Store value of query param {string} as {string}")
    public void storeQueryParamValue(String paramName, String storeKey) {
        Map<String, Object> params = session.getRequestParams();
        Object value = params.get(paramName);
        session.setValue(storeKey, value);
    }

    @Given("Set query param {string} to random value matching {string}")
    public void setRandomRegexValueInQueryParam(String paramName, String regex) {
        String randomValue = RgxGen.parse(regex).generate();
        session.getStoredValues().put(paramName, randomValue);
        session.setRequestParam(paramName, randomValue);
    }
}