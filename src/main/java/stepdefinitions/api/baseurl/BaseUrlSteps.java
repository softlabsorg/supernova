package stepdefinitions.api.baseurl;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseUrlSteps {

    private final ApiSession session;

    /**
     * Sets the base URL (protocol + host [+ port]) for all requests.
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given set base url to "https://api.example.com"
     * }</pre>
     *
     * @param baseUrl the base URL (e.g. https://api.example.com)
     */
    @Given("set base url to {string}")
    public void setBaseUrl(String baseUrl) {
        session.getRequest().baseUri(baseUrl);
    }

}