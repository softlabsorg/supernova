package stepdefinitions.api.response;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ResponseJsonPathSteps {

    private final ApiSession session;

    /**
     * Verifies that the array or collection at the given JSON path is empty.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "items" should be empty
     * }</pre>
     *
     * @param path JSON path to array/collection
     */
    @Then("json path {string} should be empty")
    public void responseJsonPathShouldBeEmpty(String path) {
        session.getResponse().then().body(path, empty());
    }

    /**
     * Verifies that the array or collection at the given JSON path is NOT empty.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "users" should not be empty
     * }</pre>
     *
     * @param path JSON path to array/collection
     */
    @Then("json path {string} should not be empty")
    public void responseJsonPathShouldNotBeEmpty(String path) {
        session.getResponse().then().body(path, not(empty()));
    }

    /**
     * Verifies that the value at JSON path is NOT null.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "data.id" should not be null
     * }</pre>
     *
     * @param path JSON path to value
     */
    @Then("json path {string} should not be null")
    public void responseJsonPathShouldNotBeNull(String path) {
        session.getResponse().then().body(path, notNullValue());
    }

    /**
     * Verifies that the value at JSON path is null.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "data.token" should be null
     * }</pre>
     *
     * @param path JSON path to value
     */
    @Then("json path {string} should be null")
    public void responseJsonPathShouldBeNull(String path) {
        session.getResponse().then().body(path, nullValue());
    }

    /**
     * Verifies that the string value at JSON path matches the given regex pattern.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "user.email" should match regex "^[\\w.+-]+@example\\.com$"
     * }</pre>
     *
     * @param path  JSON path to string
     * @param regex regex pattern
     */
    @Then("json path {string} should match regex {string}")
    public void responseJsonPathShouldMatchRegex(String path, String regex) {
        session.getResponse().then().body(path, matchesPattern(regex));
    }

}