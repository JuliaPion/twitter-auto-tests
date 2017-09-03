package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    public void openPage() {
        open("/");
    }

    public void setUsernameInput(String login) {
        $("#signin-email").val(login);
    }

    public void setPasswordInput(String password) {
        $("#signin-password").val(password);
    }

    public SelenideElement getLogInButton() {
        return $(".flex-table-secondary");
    }
}
