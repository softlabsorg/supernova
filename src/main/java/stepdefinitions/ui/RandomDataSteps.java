package stepdefinitions.ui;

import domains.ui.engine.action.Finder;
import domains.ui.session.UiSession;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import shared.utils.RandomUtils;

import java.util.List;

@RequiredArgsConstructor
public class RandomDataSteps {

    private final Finder finder;

    @And("click random element from {string}")
    public void clickRandomElementFrom(String key) {
        List<WebElement> elements = finder.findElements(key);
        int randomNum = RandomUtils.generateRandomNumber(0, elements.size());
        elements.get(randomNum).click();
    }

    @When("generate random string from regex {string} as {string}")
    public void generateRandomStringFromRegex(String regex, String key) {
        System.setProperty(key, RandomUtils.generateRandomString(regex));
    }

    @When("generate {int} chars random string as {string}")
    public void generateRandomStringAs(int length, String key) {
        String randomString = RandomUtils.generateRandomString(length);
        System.setProperty(key, randomString);
    }

}
