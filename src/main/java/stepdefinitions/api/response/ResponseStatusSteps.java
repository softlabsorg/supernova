package stepdefinitions.api.response;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseStatusSteps {

    private final ApiSession session;

    /**
     * Verifies that the HTTP response status code equals the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then status code should be 200
     * }</pre>
     *
     * @param code expected HTTP status code
     */
    @Then("status code should be {int}")
    public void validateStatusCode(int code) {
        int actualStatusCode = session.getResponse().getStatusCode();
        session.getResponse().then().statusCode(code);
    }

}