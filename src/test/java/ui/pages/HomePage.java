package ui.pages;

import com.codeborne.selenide.SelenideElement;
import ui.dialogs.RemoveDialog;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {
    public void openPage() {
        open("/");
    }

    public SelenideElement setWhatsHappeningInput(String text) {
        return $("#tweet-box-home-timeline").val(text);
    }

    public SelenideElement getTweetButton() {
        return $(".home-tweet-box").find(".js-tweet-btn");
    }

    public SelenideElement getAlertsArea() {
        return $("#message-drawer");
    }

    public String getTweetId(String tweetText) {
        return $(byText(tweetText)).closest(".js-stream-item").getAttribute("id");
    }

    public SelenideElement getRetweetCount(String tweetId) {
        return $(byId(tweetId)).find(".ProfileTweet-action--retweet");
    }

    public SelenideElement getCreatedAt(String tweetId) {
        return $(byId(tweetId)).find(".js-relative-timestamp");
    }

    public SelenideElement getText(String tweetId) {
        return $(byId(tweetId)).find(".js-tweet-text-container");
    }

    public SelenideElement getDropdownToggle(String tweetId) {
        return  $(byId(tweetId)).find(".dropdown-toggle");
    }

    public RemoveDialog clickDeleteInDropdownMenu(String tweetId) {
        $(byId(tweetId)).find(".dropdown-menu").find(".js-actionDelete").click();
        return new RemoveDialog();
    }
}
