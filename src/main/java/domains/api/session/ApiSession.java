package domains.api.session;

import domains.api.context.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@RequiredArgsConstructor
public class ApiSession {

    private final RequestContext requestContext;
    private final HeaderContext headerContext;
    private final PathParamContext pathParamContext;
    private final ParamContext paramContext;
    private final PayloadContext payloadContext;
    private final StoredDataContext storedDataContext;
    private final GlobalStoredDataContext globalStoredDataContext;

    public void killRequest() {
        requestContext.reset();
        pathParamContext.clear();
        headerContext.clear();
        paramContext.clear();
        payloadContext.clear();
    }

    public void killStoredValues() {
        storedDataContext.clear();
        requestContext.reset();
    }

    public RequestSpecification getRequest() {
        return requestContext.get();
    }

    public Response getResponse() {
        return requestContext.getResponse();
    }

    public void setResponse(Response response) {
        requestContext.setResponse(response);
    }

    public void setValue(String key, Object value) {
        storedDataContext.set(key, value);
    }

    public Object getValue(String key) {
        return storedDataContext.get(key);
    }

    public Object getPayload() {
        return payloadContext.get();
    }

    public void setPayload(Object payload) {
        payloadContext.set(payload);
    }

    public String getBasePath() {
        return requestContext.getBasePath();
    }

    public void buildAndSetFormUrlEncodedBody() {
        payloadContext.buildForm(requestContext);
    }

    public Map<String, Object> getHeaders() {
        return headerContext.get();
    }

    public void setHeader(String key, Object value) {
        headerContext.set(key, value, requestContext);
    }

    public void setPathParam(String key, Object value) {
        pathParamContext.set(key, value, requestContext);
    }

    public Map<String, Object> getRequestParams() {
        return paramContext.get();
    }

    public void setRequestParam(String key, Object value) {
        paramContext.set(key, value, requestContext);
    }

    public Map<String, Object> getStoredValues() {
        return storedDataContext.getAll();
    }

    public Object getGlobalValue(String key) {
        return globalStoredDataContext.get(key);
    }

    public void setGlobalValue(String key, Object value) {
        globalStoredDataContext.set(key, value);
    }

    public void addAccessTokenToRequest(String token) {
        requestContext.addAccessToken(token);
    }

    public void setBasePath(String path) {
        requestContext.setBasePath(path);
    }

    public void addBasePath(String path) {
        requestContext.addBasePath(path);
    }

}