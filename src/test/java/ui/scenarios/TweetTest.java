package ui.scenarios;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.dialogs.RemoveDialog;
import ui.pages.HomePage;
import ui.steps.CreateAndRemoveTweetSteps;

import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

public class TweetTest extends AbstractUITest {

    private HomePage homePage = page(HomePage.class);
    private CreateAndRemoveTweetSteps createAndRemoveTweetSteps = new CreateAndRemoveTweetSteps();

    @BeforeMethod
    public void beforeMethod() {
        homePage.openPage();
    }

    @Test
    public void testTweetCreation() throws Exception {
        String tweetText = getUniqueText();

        String tweetId = createAndRemoveTweetSteps.createTweet(tweetText);

        homePage.getCreatedAt(tweetId).shouldHave(text("s"));
        homePage.getRetweetCount(tweetId).shouldHave(cssClass("u-hiddenVisually"));
        homePage.getText(tweetId).shouldHave(text(tweetText));
    }

    @Test
    public void testTweetRemove() {
        String tweetId = createAndRemoveTweetSteps.createTweet(getUniqueText());

        homePage.getDropdownToggle(tweetId).click();

        RemoveDialog removeDialog = homePage.clickDeleteInDropdownMenu(tweetId);
        removeDialog.getDialog().should(appear);
        removeDialog.getDeleteButton().click();
        removeDialog.getDialog().should(disappear);

        homePage.getAlertsArea().shouldHave(text("Your Tweet has been deleted."));
    }

    @Test
    public void testDuplicationTweets() {
        String tweetText = getUniqueText();
        createAndRemoveTweetSteps.createTweet(tweetText);

        homePage.setWhatsHappeningInput(tweetText);
        homePage.getTweetButton().click();
        homePage.getAlertsArea().shouldHave(text("You have already sent this Tweet."));
    }

    private String getUniqueText() {
        return UUID.randomUUID().toString();
    }
}
