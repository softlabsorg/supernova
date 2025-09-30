package domains.database.session;

import domains.database.context.DatabaseConnectionContext;
import domains.database.context.DatabaseExecutionContext;
import domains.database.context.SqlQueryProviderContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class DatabaseSession {

    private final DatabaseConnectionContext connectionContext;
    private final DatabaseExecutionContext executionContext;
    private final SqlQueryProviderContext sqlQueryProviderContext;

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

    public ResultSet getCurrentResultSet() {
        return executionContext.getCurrentResultSet();
    }

    public void connectIfNeeded() {
        if (!dbConnected.get()) {
            connectAllDatabases();
            loadQueriesIfNeeded();
            dbConnected.set(true);
        }
    }

    public void loadQueriesIfNeeded() {
        sqlQueryProviderContext.loadQueriesIfNeeded();
    }

    public void clearQueryCache() {
        sqlQueryProviderContext.clear();
    }

    public String getQuery(String queryName) {
        return sqlQueryProviderContext.getQuery(queryName);
    }

    public ResultSet executeQuery(String dbName, String query) throws SQLException {
        Connection conn = getConnection(dbName);
        return executionContext.executeQuery(conn, dbName, query);
    }

    public void storeResult(String dbName, ResultSet rs) {
        executionContext.setContext(dbName, rs);
    }

}