package stepdefinitions.api.response;

import com.github.fge.jsonschema.main.JsonSchemaFactory;
import domains.api.session.ApiSession;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RequiredArgsConstructor
public class ResponseSchemaSteps {

    private final ApiSession session;

    /**
     * Validates that the response body matches the given JSON schema.
     * <p>
     * Schema files should be placed under <code>src/test/resources/schemas/</code>.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then response should match schema "user-schema.json"
     * }</pre>
     *
     * @param schemaName the filename of the schema located under resources/schemas
     */
    @SneakyThrows
    @Then("response should match schema {string}")
    public void validateResponseMatchesSchema(String schemaName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream schemaStream = classLoader.getResourceAsStream("schemas/" + schemaName);

        String schema = new String(
                Objects.requireNonNull(schemaStream, "Schema not found: " + schemaName).readAllBytes(),
                StandardCharsets.UTF_8
        );

        session.getResponse().then()
                .body(JsonSchemaValidator.matchesJsonSchema(schema)
                        .using(JsonSchemaFactory.byDefault()));
    }

}