package stepdefinitions.api.file;

import domains.api.session.ApiSession;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class FileSteps {

    private final ApiSession session;

    /**
     * Uploads a file as a multipart part using the given form field name.
     * Path is read from the local filesystem.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given upload file "/tmp/avatar.png" as multipart field "avatar"
     * }</pre>
     *
     * @param filePath  absolute or relative path to the file
     * @param paramName multipart field name
     */
    @Given("upload file {string} as multipart field {string}")
    public void uploadFile(String filePath, String paramName) {
        File file = new File(filePath);
        session.getRequest().multiPart(paramName, file);
    }

    /**
     * Downloads the latest HTTP response body to a file on disk.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Given download response to file "/tmp/response.json"
     * }</pre>
     *
     * @param filePath destination path (will be overwritten if exists)
     */
    @SneakyThrows
    @Given("download response to file {string}")
    public void downloadResponseToFile(String filePath) {
        Response response = session.getResponse();
        Files.write(Paths.get(filePath), response.getBody().asByteArray());
    }

}