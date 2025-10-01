package stepdefinitions.database;

import domains.database.session.DatabaseSession;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.testng.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.testng.AssertJUnit.*;

/**
 * Database assertion steps for verifying result sets returned by queries.
 * These steps assume that {@link DatabaseSession#getCurrentResultSet()} has already been executed.
 */
@RequiredArgsConstructor
public class DatabaseAssertionSteps {

    private final DatabaseSession databaseSession;

    /**
     * Verifies that the given column contains no NULL values.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then verify column "email" is not null
     * }</pre>
     *
     * @param column the column name to check
     */
    @Then("verify column {string} is not null")
    public void verifyColumnIsNotNull(String column) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        int rowIndex = 0;
        while (resultSet.next()) {
            rowIndex++;
            if (resultSet.getString(column) == null) {
                fail("Row " + rowIndex + " has null in column '" + column + "'");
            }
        }
    }

    /**
     * Verifies that all values in the given column are unique.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then verify column "username" all values are unique
     * }</pre>
     *
     * @param column the column name to check
     */
    @Then("verify column {string} all values are unique")
    public void verifyColumnAllValuesAreUnique(String column) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        Set<String> uniqueValues = new HashSet<>();
        int rowIndex = 0;
        while (resultSet.next()) {
            rowIndex++;
            String value = resultSet.getString(column);
            if (value != null && !uniqueValues.add(value)) {
                fail("Duplicate value found in column '" + column + "' at row " + rowIndex + ": " + value);
            }
        }
    }

    /**
     * Verifies that the given column contains at least one row with the expected value (exact match).
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then verify column "role" has value "ADMIN"
     * }</pre>
     *
     * @param column        the column name
     * @param expectedValue the expected exact value
     */
    @Then("verify column {string} has value {string}")
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
        assertTrue("Column '" + column + "' does not contain value '" + expectedValue + "'", found);
    }

    /**
     * Verifies that the given column contains at least one row where the value includes the expected substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then verify column "email" contains value "@example.com"
     * }</pre>
     *
     * @param column        the column name
     * @param expectedValue the substring expected to be present
     */
    @Then("verify column {string} contains value {string}")
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
        Assert.assertTrue(found, "Expected column '" + column + "' to contain value '" + expectedValue + "' but not found.");
    }

    /**
     * Verifies that the result set contains exactly the expected number of rows.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then verify row count is 5
     * }</pre>
     *
     * @param expectedCount the expected row count
     */
    @Then("verify row count is {int}")
    public void verifyRowCountIs(int expectedCount) throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        int rowCount = 0;
        while (resultSet.next()) {
            rowCount++;
        }
        assertEquals("Expected row count to be " + expectedCount + " but was " + rowCount, expectedCount, rowCount);
    }

    /**
     * Verifies that the result set contains at least one row.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     * Then verify at least one row exists in the result
     * }</pre>
     */
    @Then("verify at least one row exists in the result")
    public void verifyAtLeastOneRowExists() throws SQLException {
        ResultSet resultSet = databaseSession.getCurrentResultSet();
        assertTrue("Expected at least one row in the result set",
                resultSet != null && resultSet.next());
    }
}