package stepdefinitions.api.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class RawPayloadSteps {

    private final ApiSession session;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Clears the current request body (sets it to {@code null}).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given clear request body
     * }</pre>
     */
    @Given("clear request body")
    public void clearRequestBody() {
        session.getRequest().body((String) null);
    }

    /**
     * Sets a GraphQL request body (DocString) with content type {@code application/graphql+json}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set graphql payload
     * """
     * { user(id: "1") { id name } }
     * """
     * }</pre>
     *
     * @param query GraphQL query
     */
    @Given("set graphql payload")
    public void setGraphQLPayload(String query) {
        session.getRequest().body(query).contentType("application/graphql+json");
    }

    /**
     * Sets request body from a binary file with content type {@code application/octet-stream}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set binary payload from file "/tmp/data.bin"
     * }</pre>
     *
     * @param path file path
     */
    @Given("set binary payload from file {string}")
    public void setBinaryPayloadFromFile(String path) {
        session.getRequest().body(new File(path)).contentType("application/octet-stream");
    }

    /**
     * Sets raw HTML payload (DocString) with content type {@code text/html}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set raw html payload
     * """
     * <div>Hello</div>
     * """
     * }</pre>
     *
     * @param payload raw HTML content
     */
    @Given("set raw html payload")
    public void setRawHtmlPayload(String payload) {
        session.getRequest().body(payload).contentType("text/html");
    }

    /**
     * Sets raw JavaScript payload (DocString) with content type {@code application/javascript}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set raw javascript payload
     * """
     * console.log("hi");
     * """
     * }</pre>
     *
     * @param payload JavaScript code
     */
    @Given("set raw javascript payload")
    public void setRawJavaScriptPayload(String payload) {
        session.getRequest().body(payload).contentType("application/javascript");
    }

    /**
     * Sets plain text payload (DocString) with content type {@code text/plain}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set plain text payload
     * """
     * hello world
     * """
     * }</pre>
     *
     * @param payload plain text
     */
    @Given("set plain text payload")
    public void setPlainTextPayload(String payload) {
        session.getRequest().body(payload).contentType("text/plain");
    }

    /**
     * Sets raw XML payload (DocString) with content type {@code application/xml}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set raw xml payload
     * """
     * <user id="1">John</user>
     * """
     * }</pre>
     *
     * @param payload XML string
     */
    @Given("set raw xml payload")
    public void setRawXmlPayload(String payload) {
        session.getRequest().body(payload).contentType("application/xml");
    }

}