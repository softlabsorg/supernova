package stepdefinitions.api.response;

import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor
public class ResponseHeaderSteps {

    private final ApiSession session;

    /**
     * Verifies that a single response header matches the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then response header "Content-Type" should be "application/json"
     * }</pre>
     *
     * @param headerName    the response header name (e.g. Content-Type)
     * @param expectedValue expected header value
     */
    @Then("response header {string} should be {string}")
    public void responseHeaderShouldBe(String headerName, String expectedValue) {
        Response response = session.getResponse();
        assertThat(response.getHeader(headerName), equalTo(expectedValue));
    }

    /**
     * Verifies multiple response headers at once using a DataTable of name/value pairs.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then response headers should be
     *   | Content-Type | application/json |
     *   | X-Env        | prod             |
     * }</pre>
     *
     * @param table Cucumber DataTable with two columns: header name and value
     */
    @Then("response headers should be")
    public void responseHeadersShouldBe(DataTable table) {
        Response response = session.getResponse();
        table.asMap(String.class, String.class).forEach((name, value) -> {
            String actual = response.getHeader(name);
            assertThat(actual, equalTo(value));
        });
    }

    /**
     * Stores the value of a response header into session storage under a given key.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then store response header "X-Request-Id" as "requestId"
     * }</pre>
     *
     * @param headerName the header name to extract
     * @param storeKey   the session key under which the value will be stored
     */
    @Then("store response header {string} as {string}")
    public void storeResponseHeader(String headerName, String storeKey) {
        String headerValue = session.getResponse().getHeader(headerName);
        session.setValue(storeKey, headerValue);
    }

}