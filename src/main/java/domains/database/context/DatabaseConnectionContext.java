package domains.database.context;

import domains.database.models.DatabaseConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.utils.ApplicationConfigProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DatabaseConnectionContext {

    private final ApplicationConfigProvider applicationConfigProvider;
    private final Map<String, Connection> connectionMap = new HashMap<>();

    public void connectAllDatabases() {
        List<DatabaseConnection> configs = getDatabaseConfigs();

        if (configs.isEmpty()) {
            return;
        }

        configs.forEach(this::connectAndStore);
    }

    private List<DatabaseConnection> getDatabaseConfigs() {
        List<DatabaseConnection> configs = applicationConfigProvider.get().getDatabases();
        System.out.println("Loaded database configs: " + configs);
        return configs != null ? configs : List.of();
    }

    private void connectAndStore(DatabaseConnection config) {
        try {
            Connection connection = DriverManager.getConnection(
                    config.getUrl(), config.getUsername(), config.getPassword()
            );
            connectionMap.put(config.getName(), connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database: " + config.getName(), e);
        }
    }

    public Connection getConnection(String dbName) {
        Connection connection = connectionMap.get(dbName);
        if (connection == null || !isConnectionValid(connection)) {
            connectAndStore(getDatabaseConfigByName(dbName));
            connection = connectionMap.get(dbName);
        }
        return connection;
    }

    private boolean isConnectionValid(Connection connection) {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    private DatabaseConnection getDatabaseConfigByName(String dbName) {
        return getDatabaseConfigs()
                .stream()
                .filter(config -> config.getName().equals(dbName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No config found for db: " + dbName));
    }


    public void closeAllConnections() {
        for (Connection connection : connectionMap.values()) {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connectionMap.clear();
    }
}