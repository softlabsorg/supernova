package domains.database.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;

@Component
@RequiredArgsConstructor
public class DatabaseSession {

    private final DatabaseConnectionContext connectionContext;
    private final DatabaseExecutionContext executionContext;

    private final ThreadLocal<Boolean> dbConnected = ThreadLocal.withInitial(() -> false);

    public void connectAllDatabases() {
        connectionContext.connectAllDatabases();
    }

    public Connection getConnection(String dbName) {
        return connectionContext.getConnection(dbName);
    }

    public void closeAllConnections() {
        connectionContext.closeAllConnections();
        executionContext.clear();
    }

    public void setContext(String dbName, ResultSet resultSet) {
        executionContext.setContext(dbName, resultSet);
    }

    public String getCurrentDatabaseName() {
        return executionContext.getCurrentDatabaseName();
    }

    public ResultSet getCurrentResultSet() {
        return executionContext.getCurrentResultSet();
    }

    public void connectIfNeeded() {
        if (!dbConnected.get()) {
            connectAllDatabases();
            dbConnected.set(true);
        }
    }

}