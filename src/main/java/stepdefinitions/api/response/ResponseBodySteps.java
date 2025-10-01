package stepdefinitions.api.response;

import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ResponseBodySteps {

    private final ApiSession session;

    /**
     * Verifies that the raw response body does NOT contain the given substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then response body should not contain "error"
     * }</pre>
     *
     * @param value substring that must NOT appear in the raw body
     */
    @Then("response body should not contain {string}")
    public void bodyShouldNotContain(String value) {
        session.getResponse().then().body(not(containsString(value)));
    }

    /**
     * Verifies that the value at a JSON path contains the given substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "user.email" should contain "@example.com"
     * }</pre>
     *
     * @param path  JSON path to a string value
     * @param value substring to be contained
     */
    @Then("json path {string} should contain {string}")
    public void jsonPathShouldContain(String path, String value) {
        session.getResponse().then().body(path, containsString(value));
    }

    /**
     * Verifies that the value at a JSON path equals the expected value (string compare).
     * Tip: Sayısal və ya boolean dəyərlər üçün ayrıca typed müqayisələr istifadə etmək istəyə bilərsiniz.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "status" should equal "OK"
     * }</pre>
     *
     * @param path  JSON path
     * @param value expected string value
     */
    @Then("json path {string} should equal {string}")
    public void jsonPathShouldEqual(String path, String value) {
        session.getResponse().then().body(path, equalTo(value));
    }

    /**
     * Verifies that the value at a JSON path does NOT equal the expected value (string compare).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json path "status" should not equal "FAILED"
     * }</pre>
     *
     * @param path  JSON path
     * @param value string value that must NOT match
     */
    @Then("json path {string} should not equal {string}")
    public void jsonPathShouldNotEqual(String path, String value) {
        session.getResponse().then().body(path, not(equalTo(value)));
    }

    /**
     * Verifies multiple JSON paths against expected values.
     * <ul>
     *   <li>Əgər path massivdırsa: massiv expected dəyəri içində saxlamalıdır.</li>
     *   <li>Plain value-dursa: string olaraq bərabər olmalıdır.</li>
     *   <li>"null" yazılarsa: null yoxlanılır.</li>
     * </ul>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json paths should contain
     *   | user.roles | ADMIN |
     *   | meta.id    | 123   |
     *   | data       | null  |
     * }</pre>
     */
    @Then("json paths should contain")
    public void jsonPathsShouldContain(DataTable table) {
        Response response = session.getResponse();
        Map<String, Object> paths = table.asMap(String.class, Object.class);

        paths.forEach((jsonPath, expectedValue) -> {
            Object actualValue = from(response.asString()).get(jsonPath);

            if ("null".equals(expectedValue)) {
                assertThat(actualValue, nullValue());
            } else if (actualValue instanceof List<?> actualList) {
                List<String> stringList = actualList.stream().map(Object::toString).toList();
                assertThat(stringList, hasItem(String.valueOf(expectedValue)));
            } else {
                assertThat(String.valueOf(actualValue), equalTo(String.valueOf(expectedValue)));
            }
        });
    }

    /**
     * Verifies multiple JSON paths equal the given values (strict equality, string compare).
     * "null" mətni veriləndə null yoxlanılır.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then json paths should equal
     *   | status    | OK   |
     *   | data.id   | 42   |
     *   | data.note | null |
     * }</pre>
     */
    @Then("json paths should equal")
    public void jsonPathsShouldEqual(DataTable table) {
        Response response = session.getResponse();
        Map<String, Object> paths = table.asMap(String.class, Object.class);

        paths.forEach((jsonPath, expectedValue) -> {
            String actualValue = from(response.asString()).getString(jsonPath);
            if ("null".equals(expectedValue)) {
                assertThat(actualValue, nullValue());
            } else {
                assertThat(actualValue, equalTo(String.valueOf(expectedValue)));
            }
        });
    }

    /**
     * Verifies that an array at JSON path contains a value taken from temporary store.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "ids" should contain stored "createdId"
     * }</pre>
     */
    @Then("path {string} should contain stored {string}")
    public void pathShouldContainStored(String path, String key) {
        Object expected = session.getStoredValues().get(key);
        assertThat(session.getResponse().jsonPath().getList(path), hasItem(expected));
    }

    /**
     * Verifies that an array at JSON path contains ALL values from a table.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "tags" should contain values
     *   | red  |
     *   | blue |
     * }</pre>
     */
    @Then("path {string} should contain values")
    public void pathShouldContainValues(String path, DataTable table) {
        List<String> expectedValues = table.asList();
        List<String> actualValues = JsonPath.from(session.getResponse().asString()).getList(path, String.class);

        expectedValues.forEach(value -> {
            assertThat(actualValues, hasItem(value));
        });
    }

    /**
     * Verifies that an array at JSON path does NOT contain the value from temporary store.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "ids" should not contain stored "deletedId"
     * }</pre>
     */
    @Then("path {string} should not contain stored {string}")
    public void pathShouldNotContainStored(String path, String key) {
        Object expected = session.getStoredValues().get(key);
        assertThat(JsonPath.from(session.getResponse().asString()).getList(path), not(hasItem(expected)));
    }

    /**
     * Verifies that an array at JSON path does NOT contain ANY of the values in the table.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "tags" should not contain values
     *   | obsolete |
     *   | draft    |
     * }</pre>
     */
    @Then("path {string} should not contain values")
    public void pathShouldNotContainValues(String path, DataTable table) {
        List<String> expectedValues = table.asList();
        List<String> actualValues = JsonPath.from(session.getResponse().asString()).getList(path, String.class);

        expectedValues.forEach(value -> {
            assertThat(actualValues, not(hasItem(value)));
        });
    }

    /**
     * Stores a value from JSON path into GLOBAL storage (shared across scenarios).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then store json path "data.token" as global "adminToken"
     * }</pre>
     */
    @Then("store json path {string} as global {string}")
    public void storeJsonPathAsGlobal(String path, String key) {
        Object value = session.getResponse().jsonPath().get(path);
        session.setGlobalValue(key, value);
    }

    /**
     * Stores a value from JSON path into SESSION storage (current scenario).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then store json path "data.id" as "userId"
     * }</pre>
     */
    @Then("store json path {string} as {string}")
    public void storeJsonPathAsSession(String path, String key) {
        Object value = session.getResponse().jsonPath().get(path);
        session.setValue(key, value);
    }

}