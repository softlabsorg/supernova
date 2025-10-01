package domains.api.context;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ParamContext {

    private final ThreadLocal<Map<String, Object>> requestParams = ThreadLocal.withInitial(HashMap::new);

    public void set(String key, Object value, RequestContext req) {
        requestParams.get().put(key, value);
        req.get().param(key, value);
    }

    public Map<String, Object> get() {
        return requestParams.get();
    }

    public void clear() {
        requestParams.remove();
    }
}