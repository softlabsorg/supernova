package domains.api.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PayloadContext {

    private final ThreadLocal<Object> payload = ThreadLocal.withInitial(() -> null);

    public void set(Object p) {
        payload.set(p);
    }

    public Object get() {
        return payload.get();
    }

    public void clear() {
        payload.remove();
    }

    public void buildForm(RequestContext req) {
        Map<String, String> form = (Map<String, String>) payload.get();
        String body = form.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
        req.get().contentType("application/x-www-form-urlencoded").body(body);
    }
}