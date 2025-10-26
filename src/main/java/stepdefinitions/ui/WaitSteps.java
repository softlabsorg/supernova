package stepdefinitions.ui;

import domains.ui.engine.action.Finder;
import domains.ui.engine.action.Waiter;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WaitSteps {

    private final Waiter waiter;
    private final Finder finder;

    @When("wait for {int} seconds")
    public void waitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }

    @Then("verify that element {string} invisible in {int} seconds")
    public void verifyElementInvisible(String key, int time) {
        waiter.untilInvisible(finder.findBy(key), time);
    }

}
