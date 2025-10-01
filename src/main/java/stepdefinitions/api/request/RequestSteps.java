package stepdefinitions.api.request;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestSteps {

    private final ApiSession session;

    /**
     * Sends a single HTTP request with the provided method.
     * Logs the cURL and the response to the report.
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * Given send "POST" request
     * Given send "GET" request
     * }</pre>
     *
     * @param method HTTP method (e.g., GET, POST, PUT, DELETE, PATCH)
     */
    @Given("send {string} request")
    public void sendRequest(String method) {
        RequestSpecification request = session.getRequest();
        Response response = sendRequestInternal(method, request);
        session.setResponse(response);
        session.killRequest();
    }

    /**
     * Sends the specified HTTP method request multiple times sequentially.
     * Each iteration logs the cURL and the response to the report; request is reset between iterations.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given send "GET" request 3 times
     * }</pre>
     *
     * @param method HTTP method (e.g., GET, POST, PUT, DELETE, PATCH)
     * @param count  number of times to send the request (>=1)
     */
    @Given("send {string} request {int} times")
    public void sendRequestMultipleTimes(String method, int count) {

        for (int i = 1; i < count; i++) {
            RequestSpecification req = session.getRequest();
            Response resp = sendRequestInternal(method, req);
            session.setResponse(resp);
        }

        RequestSpecification request = session.getRequest();
        Response response = sendRequestInternal(method, request);
        session.setResponse(response);
        session.killRequest();
    }

    private Response sendRequestInternal(String method, RequestSpecification request) {
        return switch (method.toUpperCase()) {
            case "GET" -> request.when().get();
            case "POST" -> request.when().post();
            case "PUT" -> request.when().put();
            case "DELETE" -> request.when().delete();
            case "PATCH" -> request.when().patch();
            default -> throw new IllegalArgumentException("Invalid HTTP method: " + method);
        };
    }

}