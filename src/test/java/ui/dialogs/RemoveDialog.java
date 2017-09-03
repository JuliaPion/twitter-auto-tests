package ui.dialogs;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class RemoveDialog {
    public SelenideElement getDialog() {
        return $("#delete-tweet-dialog");
    }

    public SelenideElement getDeleteButton() {
        return getDialog().find(".delete-action");
    }
}
