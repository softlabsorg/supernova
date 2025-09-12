package stepdefinitions.api.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.curiousoddman.rgxgen.RgxGen;
import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class JsonPayloadSteps {

    private final ApiSession session;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Sets the JSON request body from a DocString with content type {@code application/json}.
     * Also stores the parsed JSON into session payload.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set json payload
     * """
     * {
     *   "name": "John",
     *   "active": true
     * }
     * """
     * }</pre>
     *
     * @param payload JSON string
     */
    @SneakyThrows
    @Given("set json payload")
    public void setJsonPayload(String payload) {
        JsonNode jsonNode = MAPPER.readTree(payload);
        session.setPayload(jsonNode);
        session.getRequest().body(jsonNode).contentType("application/json");
    }

    /**
     * Prepares a JSON structure without attaching it as request body.
     * Useful for building JSON incrementally before sending.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given prepare json structure
     * """
     * {
     *   "user": {
     *
     *    }
     *  }
     * """
     * }</pre>
     *
     * @param json JSON string
     */
    @SneakyThrows
    @Given("prepare json structure")
    public void prepareJsonStructure(String json) {
        JsonNode node = MAPPER.readTree(json);
        session.setPayload(node);
    }

    /**
     * Sets a JSON field to today's date using the provided format.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set json field "createdAt" to current date with format "yyyy-MM-dd"
     * }</pre>
     *
     * @param fieldName JSON field name
     * @param format    date format pattern
     */
    @Given("set json field {string} to current date with format {string}")
    public void setCurrentDate(String fieldName, String format) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern(format));
        ObjectNode payload = (ObjectNode) session.getPayload();
        payload.put(fieldName, date);
        session.getRequest().body(payload);

    }

    /**
     * Sets a JSON field to a date offset by N days using the provided format.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set json field "dueDate" to date plus 5 days with format "dd.MM.yyyy"
     * }</pre>
     *
     * @param fieldName JSON field name
     * @param daysToAdd number of days to add
     * @param format    date format pattern
     */
    @Given("set json field {string} to date plus {int} days with format {string}")
    public void setDatePlusDays(String fieldName, int daysToAdd, String format) {
        String date = LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(format));
        ObjectNode payload = (ObjectNode) session.getPayload();
        payload.put(fieldName, date);
        session.getRequest().body(payload);
    }

    /**
     * Sets a JSON field to a random value matching the given regex.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set json field "code" to random value matching "[A-Z]{3}[0-9]{4}"
     * }</pre>
     *
     * @param fieldName JSON field name
     * @param regex     regex pattern for random value generation
     */
    @Given("set json field {string} to random value matching {string}")
    public void setJsonFieldRandomMatching(String fieldName, String regex) {
        String random = RgxGen.parse(regex).generate();
        ObjectNode payload = (ObjectNode) session.getPayload();
        payload.put(fieldName, random);
        session.getRequest().body(payload);
    }

    /**
     * Sets a JSON field with a random value matching regex.
     * (Does not store separately; use {@code store json field} if needed.)
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set json field "pin" with random matching "[0-9]{6}"
     * }</pre>
     *
     * @param fieldName JSON field name
     * @param regex     regex pattern for random value generation
     */
    @Given("set json field {string} with random matching {string}")
    public void setAndStoreRandomJsonField(String fieldName, String regex) {
        String randomValue = RgxGen.parse(regex).generate();
        ObjectNode payload = (ObjectNode) session.getPayload();
        payload.put(fieldName, randomValue);
        session.getRequest().body(payload);
    }

    /**
     * Stores a value from the current JSON payload into session storage.
     * Supports {@code string}, {@code number}, and {@code boolean} node types.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given store json field "id" as "userId"
     * }</pre>
     *
     * @param jsonField JSON field name
     * @param storeKey  key under which the value will be stored
     */
    @Given("store json field {string} as {string}")
    public void storeJsonField(String jsonField, String storeKey) {
        Object payload = session.getPayload();
        Object value = null;

        if (payload instanceof JsonNode jsonNodePayload) {
            JsonNode valueNode = jsonNodePayload.get(jsonField);
            if (valueNode != null) {
                if (valueNode.isInt() || valueNode.isLong()) {
                    value = valueNode.numberValue();
                } else if (valueNode.isBoolean()) {
                    value = valueNode.booleanValue();
                } else {
                    value = valueNode.asText();
                }
            }
        }
        session.setValue(storeKey, value);
    }

    /**
     * Sets a JSON field using a value from session storage.
     * Preserves numeric/boolean types and updates the request body.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set json field "userId" as stored "createdUserId"
     * }</pre>
     *
     * @param fieldName JSON field name
     * @param storedKey session key holding the value
     */
    @Given("set json field {string} as stored {string}")
    public void setJsonFieldFromStored(String fieldName, String storedKey) {
        Object value = session.getValue(storedKey);
        ObjectNode payload = (ObjectNode) session.getPayload();

        switch (value) {
            case Integer i -> payload.put(fieldName, i);
            case Long l -> payload.put(fieldName, l);
            case Boolean b -> payload.put(fieldName, b);
            case Number number -> payload.put(fieldName, number.doubleValue());
            case null, default -> payload.put(fieldName, String.valueOf(value));
        }

        session.getRequest().body(payload);
    }

}