package domains.api.context;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HeaderContext {

    private final ThreadLocal<Map<String, Object>> headers = ThreadLocal.withInitial(HashMap::new);

    public void set(String key, Object value, RequestContext req) {
        headers.get().put(key, value);
        req.get().header(key, value);
    }

    public Map<String, Object> get() {
        return headers.get();
    }

    public void clear() {
        headers.remove();
    }
}