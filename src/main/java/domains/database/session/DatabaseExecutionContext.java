package domains.database.session;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Getter
@Component
public class DatabaseExecutionContext {

    private String currentDatabaseName;

    private ResultSet currentResultSet;

    public void setContext(String dbName, ResultSet resultSet) {
        this.currentDatabaseName = dbName;
        this.currentResultSet = resultSet;
    }

    public void clear() {
        this.currentDatabaseName = null;
        this.currentResultSet = null;
    }

}