package stepdefinitions.api.endpoint;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EndpointSteps {

    private final ApiSession session;

    /**
     * Sets the endpoint path relative to the base URL.
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set endpoint to "/users/123"
     * }</pre>
     *
     * @param endpoint the endpoint path relative to the base URL
     */
    @Given("set endpoint to {string}")
    public void setEndpoint(String endpoint) {
        session.setBasePath(endpoint);
    }

}