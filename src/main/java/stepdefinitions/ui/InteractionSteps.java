package stepdefinitions.ui;

import domains.ui.engine.action.*;
import domains.ui.session.UiSession;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InteractionSteps {

    private final Clicker clicker;
    private final Typer typer;
    private final Selector selector;
    private final Hoverer hoverer;
    private final Scroller scroller;
    private final Finder finder;

    @When("click on {string}")
    public void iClickOnUpper(String locatorKey) {
        clicker.click(locatorKey);
    }

    @When("select {string} from dropdown {string}")
    public void selectFromDropdown(String visibleText, String locatorKey) {
        selector.selectByVisibleText(locatorKey, visibleText);
    }

    @When("hover on {string}")
    public void hoverOn(String locatorKey) {
        hoverer.hover(locatorKey);
    }

    @When("scroll to {string}")
    public void scrollTo(String locatorKey) {
        scroller.scrollTo(locatorKey);
    }

    @When("paste {string} to {string}")
    public void typeTo(String text, String locatorKey) {
        typer.sendKeys(locatorKey, text);
    }

    @When("type {string} to {string}")
    public void userFillsFieldByActions(String text, String key) {
        typer.sendKeysWithActions(key, text);
    }
    
    @When("save {string} element value to {string} global variable")
    public void saveElementValueToGlobalVariable(String locatorKey, String variableName) {
        String text = finder.findElement(locatorKey).getText();
        System.setProperty(variableName, text);
    }

    @When("type {string} global variable to {string} field")
    public void typeGlobalVariableTo(String variableName, String locatorKey) {
        String text = System.getProperty(variableName);
        typer.sendKeysWithActions(locatorKey, text);
    }

    @When("paste {string} global variable to {string} field")
    public void pasteGlobalVariableTo(String variableName, String locatorKey) {
        String text = System.getProperty(variableName);
        typer.sendKeysWithActions(locatorKey, text);
    }
    
    





}