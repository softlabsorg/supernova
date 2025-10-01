package stepdefinitions.database;

import domains.database.session.DatabaseSession;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
public class DatabaseQueryExecutionSteps {

    private final DatabaseSession databaseSession;

    /**
     * Executes a SQL query against the specified database.
     * <p>
     * - If the query produces a {@link ResultSet} (e.g., SELECT), the result set is stored
     *   in the {@link DatabaseSession} context for later assertions.
     * - If the query is an update (INSERT, UPDATE, DELETE), the affected row count is logged
     *   and the context is set to {@code null}.
     *
     * <p><b>Examples:</b></p>
     * <pre>{@code
     * When execute query "SELECT * FROM users" on database "mainDb"
     * When execute query "UPDATE users SET active = false WHERE id = 1" on database "auditDb"
     * }</pre>
     *
     * @param query  the SQL query to execute (e.g. SELECT, INSERT, UPDATE, DELETE)
     * @param dbName the database identifier (as managed by {@link DatabaseSession})
     * @throws SQLException if execution fails
     */
    @When("execute query {string} on database {string}")
    public void executeQueryOnDatabaseUpper(String query, String dbName) throws SQLException {
        Connection connection = databaseSession.getConnection(dbName);
        Statement statement = connection.createStatement();

        boolean hasResultSet = statement.execute(query);

        if (hasResultSet) {
            ResultSet resultSet = statement.getResultSet();
            databaseSession.setContext(dbName, resultSet);
        } else {
            int updateCount = statement.getUpdateCount();
            System.out.printf("Query executed on [%s]. Affected rows: %d%n", dbName, updateCount);
            databaseSession.setContext(dbName, null);
        }
    }

}