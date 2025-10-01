package stepdefinitions.database;

import domains.database.session.DatabaseSession;
import io.cucumber.docstring.DocString;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class DatabaseQueryExecutionSteps {

    private final DatabaseSession databaseSession;

    /**
     * Executes a SQL query against the specified database.
     * <p>
     * Supports multi-line queries using Cucumber DocString.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * When execute query on database "test_db"
     * """
     *   SELECT *
     *   FROM users
     *   WHERE active = true
     * """
     * }</pre>
     *
     * @param dbName    the database identifier (as managed by {@link DatabaseSession})
     * @param docString the SQL query block
     * @throws SQLException if execution fails
     */
    @When("execute query on database {string}")
    public void executeQueryOnDatabase(String dbName, DocString docString) throws SQLException {
        ResultSet rs = databaseSession.executeQuery(dbName, docString.getContent());
        databaseSession.storeResult(dbName, rs);
    }

    /**
     * Executes a pre-registered SQL query by name on the specified database.
     * <p>
     * Queries are resolved from the query registry managed by {@link DatabaseSession}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * When execute sql "delete_user_by_email" on database "test_db"
     * }</pre>
     *
     * @param queryName the identifier of the SQL query in registry
     * @param dbName    the database identifier (as managed by {@link DatabaseSession})
     * @throws SQLException if execution fails
     */
    @When("execute sql {string} on database {string}")
    public void executeSqlFromRegistry(String queryName, String dbName) throws SQLException {
        String query = databaseSession.getQuery(queryName);
        ResultSet rs = databaseSession.executeQuery(dbName, query);
        databaseSession.storeResult(dbName, rs);
    }

}