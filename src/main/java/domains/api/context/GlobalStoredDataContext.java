package domains.api.context;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GlobalStoredDataContext {

    private final Map<String, Object> GLOBAL_STORE = new ConcurrentHashMap<>();

    public void set(String key, Object value) {
        GLOBAL_STORE.put(key, value);
    }

    public Object get(String key) {
        return GLOBAL_STORE.get(key);
    }

    public void clear() {
        GLOBAL_STORE.clear();
    }
}