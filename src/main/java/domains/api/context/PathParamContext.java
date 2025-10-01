package domains.api.context;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PathParamContext {

    private final ThreadLocal<Map<String, Object>> pathParams = ThreadLocal.withInitial(HashMap::new);

    public void set(String key, Object value, RequestContext req) {
        pathParams.get().put(key, value);
        req.get().pathParam(key, value);
    }

    public Map<String, Object> get() {
        return pathParams.get();
    }

    public void clear() {
        pathParams.remove();
    }
}