package stepdefinitions.ui;

import domains.ui.engine.action.Navigator;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NavigationSteps {

    private final Navigator navigator;

    @Given("navigate to {string} url")
    public void navigate(String baseUrl) {
        navigator.navigate(baseUrl);
    }

    @Given("navigate to {string} url with {string} endpoint")
    public void navigat(String baseUrl, String target) {
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
