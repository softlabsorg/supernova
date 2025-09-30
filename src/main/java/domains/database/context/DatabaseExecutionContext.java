package domains.database.context;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
@Component
public class DatabaseExecutionContext {

    private String currentDatabaseName;
    private ResultSet currentResultSet;

    public ResultSet executeQuery(Connection connection, String dbName, String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            boolean hasResultSet = statement.execute(query);

            if (hasResultSet) {
                return statement.getResultSet();
            } else {
                int updateCount = statement.getUpdateCount();
                System.out.printf("Query executed on [%s]. Affected rows: %d%n", dbName, updateCount);
                return null;
            }
        }
    }

    public void setContext(String dbName, ResultSet resultSet) {
        this.currentDatabaseName = dbName;
        this.currentResultSet = resultSet;
    }

    public void clear() {
        this.currentDatabaseName = null;
        this.currentResultSet = null;
    }

}