package stepdefinitions.ui;

import domains.ui.engine.validation.EnabledVerifier;
import domains.ui.engine.validation.TextVerifier;
import domains.ui.engine.validation.VisibilityVerifier;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationSteps {

    private final EnabledVerifier enabledVerifier;
    private final TextVerifier textVerifier;
    private final VisibilityVerifier visibilityVerifier;

    @Then("verify that {string} is enable")
    public void verifyElementActive(String locatorKey) {
        enabledVerifier.verifyEnabled(locatorKey);
    }

    @Then("verify text of {string} is {string}")
    public void verifyTextIs(String locatorKey, String expectedText) {
        textVerifier.verifyText(locatorKey, expectedText);
    }

    @Then("verify that element {string} is visible")
    public void verifyElementVisible(String locatorKey) {
        visibilityVerifier.verifyVisible(locatorKey);
    }

}