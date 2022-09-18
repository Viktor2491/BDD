package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static java.time.Duration.ofSeconds;

public class UpLoadPage {
    private SelenideElement amountField = $("div[data-test-id=amount] input");
    private SelenideElement fromField = $("span[data-test-id=from] input");
    private SelenideElement uploadButton = $("button[data-test-id=action-transfer]");
    private SelenideElement errorBox = $("[data-test-id = error-notification]");


    public DashboardPage shouldTransferMoneyBetweenCards(String amount, String cardNumber) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        amountField.setValue(amount);
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        fromField.setValue(cardNumber);
        sleep(10000);
        uploadButton.click();
        return new DashboardPage();
    }

    public void shouldNotTransferMoneyBetweenCards(String amount, String cardNumber) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        amountField.setValue(amount);
        errorBox.shouldBe(Condition.visible, ofSeconds(15))
                .shouldHave(text("Ошибка"));
        return;
    }
}
