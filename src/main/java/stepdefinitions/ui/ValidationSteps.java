package stepdefinitions.ui;

import domains.ui.engine.assertion.TextAssertion;
import domains.ui.engine.assertion.StateAssertion;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationSteps {

    private final StateAssertion stateAssertion;
    private final TextAssertion textVerifier;
    private final StateAssertion visibilityVerifier;

    @Then("verify that {string} is enable")
    public void verifyElementActive(String locatorKey) {
        stateAssertion.assertEnabled(locatorKey);
    }

    @Then("verify text of {string} is {string}")
    public void verifyTextIs(String locatorKey, String expectedText) {
        textVerifier.assertTextEquals(locatorKey, expectedText);
    }

    @Then("verify that element {string} is visible")
    public void verifyElementVisible(String locatorKey) {
        visibilityVerifier.assertVisible(locatorKey);
    }

    @Then("verify that element {string} is not visible")
    public void verifyElementNotVisible(String locatorKey) {
        visibilityVerifier.assertNotVisible(locatorKey);
    }

    @Then("verify text of {string} contains {string}")
    public void verifyTextContains(String locatorKey, String expectedText) {
        textVerifier.assertTextContains(locatorKey, expectedText);
    }

    @Then("verify {string} element equals {string} global variable")
    public void verifyElementEqualsGlobalVariable(String locatorKey, String variableName) {
        String expectedText = System.getProperty(variableName);
        textVerifier.assertTextEquals(locatorKey, expectedText);
    }

    @Then("verify {string} element contains {string} global variable")
    public void verifyElementContainsGlobalVariable(String locatorKey, String variableName) {
        String expectedText = System.getProperty(variableName);
        textVerifier.assertTextContains(locatorKey, expectedText);
    }

}