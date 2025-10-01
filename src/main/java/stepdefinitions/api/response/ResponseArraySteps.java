package stepdefinitions.api.response;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ResponseArraySteps {

    private final ApiSession session;

    /**
     * Asserts that all items in the array at the given JSON path are exactly equal to the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then all items at path "users.role" should be "ADMIN"
     * }</pre>
     *
     * @param path  JSON path to an array
     * @param value expected value for each item
     */
    @Then("all items at path {string} should be {string}")
    public void allItemsShouldBe(String path, String value) {
        List<Object> list = from(session.getResponse().asString()).getList(path);
        assertThat("Not all items at path [" + path + "] are equal to: " + value, list, everyItem(equalTo(value)));
    }

    /**
     * Asserts that all items in the array at the given JSON path contain the expected substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then all items at path "users.email" should contain "@example.com"
     * }</pre>
     *
     * @param path  JSON path to an array of strings
     * @param value substring that each item must contain
     */
    @Then("all items at path {string} should contain {string}")
    public void allItemsContain(String path, String value) {
        List<String> list = from(session.getResponse().asString()).getList(path);
        assertThat(list, everyItem(containsString(value)));
    }

    /**
     * Asserts that the array at the given JSON path contains all of the specified integers (DataTable format).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "ids" should contain integers
     *   | 1 |
     *   | 2 |
     *   | 3 |
     * }</pre>
     *
     * @param path     JSON path to an array of integers
     * @param integers integers to be present in the array
     */
    @Then("path {string} should contain integers")
    public void pathShouldContainIntegers(String path, List<Integer> integers) {
        Response response = session.getResponse();
        integers.forEach(expected -> {
            response.then().body(path, hasItem(expected));
        });
    }

    /**
     * Same as above, but supports a comma-separated list via the custom {intlist} ParameterType.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "ids" should contain integers 1,2,3
     * }</pre>
     */
    @Then("path {string} should contain integers {intlist}")
    public void pathShouldContainIntegersList(String path, List<Integer> integers) {
        pathShouldContainIntegers(path, integers);
    }

    /**
     * Asserts that the array at the given JSON path does NOT contain any of the specified integers (DataTable format).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "ids" should not contain integers
     *   | 99  |
     *   | 100 |
     * }</pre>
     *
     * @param path     JSON path to an array of integers
     * @param integers integers that must NOT be present in the array
     */
    @Then("path {string} should not contain integers")
    public void pathShouldNotContainIntegers(String path, List<Integer> integers) {
        Response response = session.getResponse();
        integers.forEach(notExpected -> {
            response.then().body(path, not(hasItem(notExpected)));
        });
    }

    /**
     * Also supports comma-separated input via {intlist}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then path "ids" should not contain integers 99,100
     * }</pre>
     */
    @Then("path {string} should not contain integers {intlist}")
    public void pathShouldNotContainIntegersList(String path, List<Integer> integers) {
        pathShouldNotContainIntegers(path, integers);
    }

    /**
     * Asserts that the array size at the given JSON path equals N.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then size of path "users" should be 5
     * }</pre>
     *
     * @param path JSON path to an array
     * @param size expected exact size
     */
    @Then("size of path {string} should be {int}")
    public void sizeShouldBeEqual(String path, int size) {
        session.getResponse().then().body(path + ".size()", equalTo(size));
    }

    /**
     * Asserts that the array size at the given JSON path is greater than or equal to N.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then size of path "users" should be greater than or equal to 5
     * }</pre>
     */
    @Then("size of path {string} should be greater than or equal to {int}")
    public void sizeShouldBeGreaterThanEqual(String path, int size) {
        session.getResponse().then().body(path + ".size()", greaterThanOrEqualTo(size));
    }

    /**
     * Asserts that the array size at the given JSON path is strictly greater than N.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then size of path "users" should be greater than 3
     * }</pre>
     */
    @Then("size of path {string} should be greater than {int}")
    public void sizeShouldBeGreaterThan(String path, int size) {
        session.getResponse().then().body(path + ".size()", greaterThan(size));
    }

    /**
     * Asserts that the array size at the given JSON path is less than or equal to N.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then size of path "users" should be less than or equal to 10
     * }</pre>
     */
    @Then("size of path {string} should be less than or equal to {int}")
    public void sizeShouldBeLessThanEqual(String path, int size) {
        session.getResponse().then().body(path + ".size()", lessThanOrEqualTo(size));
    }

    /**
     * Asserts that the array size at the given JSON path is strictly less than N.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then size of path "users" should be less than 20
     * }</pre>
     */
    @Then("size of path {string} should be less than {int}")
    public void sizeShouldBeLessThan(String path, int size) {
        session.getResponse().then().body(path + ".size()", lessThan(size));
    }

    /**
     * Asserts that every array item has a given field present.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then every array item should have field "company_id"
     * }</pre>
     *
     * @param fieldName the field expected in every item
     */
    @Then("every array item should have field {string}")
    public void everyArrayItemShouldHaveField(String fieldName) {
        List<Map<String, Object>> items = from(session.getResponse().asString()).getList("$");
        assertThat(items.size(), greaterThan(0));

        for (Map<String, Object> item : items) {
            assertThat(item.containsKey(fieldName), is(true));
        }
    }

    /**
     * Asserts that every array item field at the given key matches a UUID pattern.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then every array item field "company_id" should match uuid pattern
     * }</pre>
     *
     * @param fieldName field to validate
     */
    @Then("every array item field {string} should match uuid pattern")
    public void everyArrayItemFieldShouldMatchUuidPattern(String fieldName) {
        List<String> values = from(session.getResponse().asString()).getList("$." + fieldName);
        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        values.forEach(value -> assertThat(value.matches(uuidRegex), is(true)));
    }

    /**
     * Asserts that every array item field is not null or empty string.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then every array item field "name" should not be empty
     * }</pre>
     *
     * @param fieldName field to validate
     */
    @Then("every array item field {string} should not be empty")
    public void everyArrayItemFieldShouldNotBeEmpty(String fieldName) {
        List<String> values = from(session.getResponse().asString()).getList("$." + fieldName);
        values.forEach(value -> assertThat(value, allOf(notNullValue(), not(emptyString()))));
    }

    /**
     * Asserts that every array item field is of type integer.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then every array item field "role" should be integer
     * }</pre>
     *
     * @param fieldName field to validate
     */
    @Then("every array item field {string} should be integer")
    public void everyArrayItemFieldShouldBeInteger(String fieldName) {
        List<Object> values = from(session.getResponse().asString()).getList("$." + fieldName);
        values.forEach(value -> assertThat(value, instanceOf(Integer.class)));
    }

}