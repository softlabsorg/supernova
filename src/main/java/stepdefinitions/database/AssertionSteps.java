package stepdefinitions.database;

import domains.database.session.DatabaseSession;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@RequiredArgsConstructor
public class AssertionSteps {

    private final DatabaseSession databaseSession;

    @Then("Verify at least one row exists in the result")
    public void verifyAtLeastOneRowExists() throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        assertTrue(resultSet != null && resultSet.next());
    }

    @Then("Verify column {string} has value {string}")
    public void verifyColumnHasValue(String column, String expectedValue) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        boolean found = false;
        while (resultSet.next()) {
            String actualValue = resultSet.getString(column);
            if (expectedValue.equals(actualValue)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Then("Verify row count is {int}")
    public void verifyRowCountIs(int expectedCount) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        int rowCount = 0;
        while (resultSet.next()) {
            rowCount++;
        }
        assertEquals(expectedCount, rowCount);
    }

    @Then("Verify column {string} contains value {string}")
    public void verifyColumnContainsValue(String column, String expectedValue) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        boolean found = false;
        while (resultSet.next()) {
            String actualValue = resultSet.getString(column);
            if (actualValue != null && actualValue.contains(expectedValue)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Then("Verify column {string} is not null")
    public void verifyColumnIsNotNull(String column) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        boolean allNotNull = true;
        while (resultSet.next()) {
            if (resultSet.getString(column) == null) {
                allNotNull = false;
                break;
            }
        }
        assertTrue(allNotNull);
    }

    @Then("Verify column {string} all values are unique")
    public void verifyColumnAllValuesAreUnique(String column) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        Set<String> uniqueValues = new HashSet<>();
        boolean isUnique = true;
        while (resultSet.next()) {
            String value = resultSet.getString(column);
            if (value != null && !uniqueValues.add(value)) {
                isUnique = false;
                break;
            }
        }
        assertTrue(isUnique);
    }

}