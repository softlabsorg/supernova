package domains.database.context;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Component
public class SqlQueryProviderContext {

    private final Map<String, String> queries = new HashMap<>();
    private boolean loaded = false;

    public void loadQueriesIfNeeded() {
        if (loaded) return;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(SqlQueryProviderContext.class.getClassLoader().getResourceAsStream("sql/queries.sql"))
        ))) {
            String content = reader.lines().collect(Collectors.joining("\n"));
            String[] blocks = content.split("-- ");

            for (String block : blocks) {
                if (block.trim().isEmpty()) continue;

                int newlineIndex = block.indexOf("\n");
                if (newlineIndex == -1) continue;

                String name = block.substring(0, newlineIndex).trim();
                String query = block.substring(newlineIndex).trim();

                queries.put(name, query);
            }

            loaded = true;
            System.out.printf("✅ Loaded %d SQL queries from queries.sql%n", queries.size());

        } catch (Exception e) {
            System.out.println("⚠️ No SQL query file found at classpath: sql/queries.sql → Skipping load.");
            loaded = true;
        }
    }

    public String getQuery(String name) {
        if (!loaded) {
            loadQueriesIfNeeded();
        }
        String query = queries.get(name);
        if (query == null) {
            throw new RuntimeException("No query found with name: " + name);
        }
        return query;
    }

    public void clear() {
        queries.clear();
        loaded = false;
    }


}