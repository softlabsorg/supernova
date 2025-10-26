package stepdefinitions.ui;

import domains.ui.engine.action.Navigator;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NavigationSteps {

    private final Navigator navigator;

    @Given("navigate to base url")
    public void navigate() {
        navigator.navigate();
    }

    @Given("navigate to site url with {string} endpoint")
    public void navigate(String target) {
        navigator.navigate(target);
    }

    @Given("navigate back")
    public void navigateBack() {
        navigator.navigateBack();
    }

    @Given("navigate forward")
    public void navigateForward() {
        navigator.navigateForward();
    }

    @Given("refresh the page")
    public void refreshThePage() {
        navigator.refreshPage();
    }

}
