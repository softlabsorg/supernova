package stepdefinitions.ui;

import domains.ui.engine.interaction.*;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InteractionSteps {

    private final Clicker clicker;
    private final Typer typer;
    private final Selector selector;
    private final Hoverer hoverer;
    private final Scroller scroller;

    @Given("click on {string}")
    public void iClickOnUpper(String locatorKey) {
        clicker.click(locatorKey);
    }

    @Given("select {string} from dropdown {string}")
    public void selectFromDropdown(String visibleText, String locatorKey) {
        selector.selectByVisibleText(locatorKey, visibleText);
    }

    @Given("hover on {string}")
    public void hoverOn(String locatorKey) {
        hoverer.hover(locatorKey);
    }

    @Given("scroll to {string}")
    public void scrollTo(String locatorKey) {
        scroller.scrollTo(locatorKey);
    }

    @Given("type {string} to {string}")
    public void typeTo(String text, String locatorKey) {
        typer.sendKeys(locatorKey, text);
    }

}