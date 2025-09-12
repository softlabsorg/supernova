package stepdefinitions.api.payload;

import domains.api.session.ApiSession;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class FormPayloadSteps {

    private final ApiSession session;

    /**
     * Sets an {@code application/x-www-form-urlencoded} payload from a Key/Value table
     * and attaches the encoded body to the request.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set form payload
     *   | username | john  |
     *   | password | 12345 |
     * }</pre>
     *
     * @param dataTable table containing form field names and values
     */
    @Given("set form payload")
    public void setFormPayload(DataTable dataTable) {
        Map<String, String> payload = new HashMap<>(dataTable.asMap(String.class, String.class));
        session.setPayload(payload);
        session.buildAndSetFormUrlEncodedBody();
    }

    /**
     * Uses a value from session storage as a form field, rebuilds and sets the form body.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set form field "otp" as stored "lastOtp"
     * }</pre>
     *
     * @param fieldName form field name
     * @param storedKey key from which to retrieve the stored value
     */
    @Given("set form field {string} as stored {string}")
    public void useStoredAsFormField(String fieldName, String storedKey) {
        Object value = session.getValue(storedKey);
        Object payloadObject = session.getPayload();

        if (payloadObject instanceof Map<?, ?> payloadMap) {
            @SuppressWarnings("unchecked")
            Map<String, String> payload = (Map<String, String>) payloadMap;
            payload.put(fieldName, String.valueOf(value));
            session.setPayload(payload);
            session.buildAndSetFormUrlEncodedBody();
        } else {
            throw new IllegalStateException("Payload is not a Map<String, String> (form)!");
        }
    }

}