package stepdefinitions.api.payload;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class MultipartPayloadSteps {

    private final ApiSession session;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Adds one or more files (comma-separated classpath paths) as the same multipart field.
     * Each file is sent with content type {@code application/pdf}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given add files "files/a.pdf, files/b.pdf" as multipart field "attachments"
     * }</pre>
     *
     * @param csvFiles  comma-separated file paths in classpath
     * @param fieldName multipart field name
     */
    @SneakyThrows
    @Given("add files {string} as multipart field {string}")
    public void addFilesAsField(String csvFiles, String fieldName) {
        String[] filePaths = csvFiles.split(",");
        for (String filePath : filePaths) {
            File file = getFileFromClasspath(filePath.trim());
            try (InputStream inputStream = new FileInputStream(file)) {
                session.getRequest().multiPart(fieldName, file.getName(), inputStream, "application/pdf");
            }
        }

    }

    /**
     * Adds the current JSON payload as a JSON multipart field with the given name.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given add json payload as multipart field "metadata"
     * }</pre>
     *
     * @param fieldName multipart field name
     */
    @SneakyThrows
    @Given("add json payload as multipart field {string}")
    public void addPayloadAsMultipartField(String fieldName) {
        JsonNode payload = (JsonNode) session.getPayload();
        String json = MAPPER.writeValueAsString(payload);
        session.getRequest().multiPart(fieldName, null, json, "application/json");
    }

    /**
     * Sets multipart form data from a table where values may be plain strings or file paths.
     * File paths that exist are sent as files; others as text parts.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set multipart form data
     *   | description | sample text  |
     *   | file        | /tmp/img.png |
     * }</pre>
     *
     * @param formData Map containing form fields and values
     */
    @Given("set multipart form data")
    public void setMultipartFormData(Map<String, String> formData) {
        Map<String, String> form = new HashMap<>();
        Map<String, File> files = new HashMap<>();

        formData.forEach((key, value) -> {
            String safeValue = (value != null) ? value : "";
            File file = new File(safeValue);
            if (file.exists()) {
                files.put(key, file);
            } else {
                form.put(key, safeValue);
            }
        });

        form.forEach((key, value) -> session.getRequest().multiPart(key, value));
        files.forEach((key, file) -> session.getRequest().multiPart(key, file, "application/octet-stream"));
    }

    /**
     * Sets a multipart field containing JSON (DocString) and stores the parsed JSON into session payload.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given set multipart json payload field "payload"
     * """
     * { "name": "John", "age": 30 }
     * """
     * }</pre>
     *
     * @param fieldName   multipart field name
     * @param jsonPayload JSON string
     */
    @SneakyThrows
    @Given("set multipart json payload field {string}")
    public void setMultipartJsonPayload(String fieldName, String jsonPayload) {
        JsonNode node = MAPPER.readTree(jsonPayload);
        session.setPayload(node);
        session.getRequest().multiPart(fieldName, MAPPER.writeValueAsString(node), "application/json");
    }

    private File getFileFromClasspath(String path) {
        URL resource = getClass().getClassLoader().getResource(path);
        if (resource == null) throw new IllegalArgumentException("Not found in classpath: " + path);
        try {
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}