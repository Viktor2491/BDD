package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static java.time.Duration.ofSeconds;

public class UpLoadPage {
    private SelenideElement addMoneyHeading = $(withText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id='amount'] [type='text']");
    private SelenideElement fromField = $("[data-test-id='from'] [type='tel']");
    private SelenideElement uploadButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorBox = $("[data-test-id=error-notification]");

    public UpLoadPage() {
        addMoneyHeading.shouldBe(Condition.visible, ofSeconds(10));
    }

    public DashboardPage shouldTransferMoneyBetweenCards(int amount, DataHelper.CardInfo cardInfo) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(cardInfo.getCardNumber());
        uploadButton.click();
        return new DashboardPage();
    }
}