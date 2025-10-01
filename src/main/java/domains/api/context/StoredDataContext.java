package domains.api.context;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StoredDataContext {

    private final ThreadLocal<Map<String, Object>> stored = ThreadLocal.withInitial(HashMap::new);
    private final ThreadLocal<String> token = ThreadLocal.withInitial(() -> "");

    public void set(String key, Object value) {
        stored.get().put(key, value);
    }

    public Object get(String key) {
        return stored.get().get(key);
    }

    public Map<String, Object> getAll() {
        return stored.get();
    }

    public void clear() {
        stored.remove();
        token.remove();
    }

    public void refreshToken(RequestContext req) {
        req.addAccessToken(token.get());
    }

    public String getToken() {
        return token.get();
    }

    public void setToken(String t) {
        token.set(t);
    }

}