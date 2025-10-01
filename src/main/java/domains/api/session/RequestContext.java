package domains.api.session;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

@Component
public class RequestContext {

    private final ThreadLocal<RequestSpecification> request = ThreadLocal.withInitial(() -> RestAssured.given().log().all());
    private final ThreadLocal<Response> response = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> basePath = ThreadLocal.withInitial(() -> "");

    public RequestSpecification get() {
        if (request.get() == null) request.set(RestAssured.given().log().all());
        return request.get();
    }

    public void reset() {
        request.remove();
        basePath.remove();
    }

    public Response getResponse() {
        return response.get();
    }

    public void setResponse(Response res) {
        response.set(res);
    }

    public String getBasePath() {
        return basePath.get();
    }

    public void addAccessToken(String token) {
        get().header("Authorization", "Bearer " + token);
    }

    public void setBasePath(String path) {
        basePath.set(path);
        get().basePath(path);
    }

    public void addBasePath(String suffix) {
        String current = basePath.get();
        if (current == null || current.isBlank()) {
            basePath.set(suffix);
            get().basePath(suffix);
        } else {
            String combined = current.endsWith("/") ? current + suffix : current + "/" + suffix;
            basePath.set(combined);
            get().basePath(combined);
        }
    }
}