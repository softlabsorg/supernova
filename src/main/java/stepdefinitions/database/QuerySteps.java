package stepdefinitions.database;

import domains.database.session.DatabaseSession;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
public class QuerySteps {

    private final DatabaseSession databaseSession;

    @When("Execute SQL: {string} on {string}")
    public void executeSqlOnDatabase(String query, String dbName) throws SQLException {
        Connection connection = databaseSession.getConnection(dbName);
        Statement statement = connection.createStatement();

        boolean hasResultSet = statement.execute(query);

        if (hasResultSet) {
            ResultSet resultSet = statement.getResultSet();
            databaseSession.setContext(dbName, resultSet);
        } else {
            int updateCount = statement.getUpdateCount();
            System.out.println("Query executed. Affected rows: " + updateCount);
            databaseSession.setContext(dbName, null);
        }
    }

}