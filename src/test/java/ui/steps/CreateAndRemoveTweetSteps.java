package ui.steps;

import ui.pages.HomePage;

import static com.codeborne.selenide.Selenide.page;

public class CreateAndRemoveTweetSteps {
    private HomePage homePage = page(HomePage.class);

    public String createTweet(String tweetText) {
        homePage.setWhatsHappeningInput(tweetText);
        homePage.getTweetButton().click();
        return homePage.getTweetId(tweetText);
    }
}
