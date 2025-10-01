package stepdefinitions.api.response;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import static org.hamcrest.Matchers.lessThan;

@RequiredArgsConstructor
public class ResponseTimeSteps {

    private final ApiSession session;

    /**
     * Verifies that the HTTP response time is less than the given number of milliseconds.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then response time should be less than 1000
     * }</pre>
     *
     * @param ms maximum expected response time in milliseconds
     */
    @Then("response time should be less than {int}")
    public void validateResponseTime(long ms) {
        session.getResponse().then().time(lessThan(ms));
    }

}