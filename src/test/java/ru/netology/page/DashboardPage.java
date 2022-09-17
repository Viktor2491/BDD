package ru.netology.page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


    public class DashboardPage {
        private SelenideElement heading = $("[data-test-id=dashboard]");
        private ElementsCollection cards = $$(".list__item");
        private final String balanceStart = "баланс: ";
        private final String balanceFinish = " р.";

        public DashboardPage() {
            heading.shouldBe(Condition.visible);
        }

        public int getCardsBalance(String lastDigits) {
            val balance = cards.findBy(Condition.text(lastDigits)).text();
            val start = balance.indexOf(balanceStart);
            val finish = balance.indexOf(balanceFinish);
            val value = balance.substring(start + balanceStart.length(), finish).trim();
            return Integer.parseInt(value);
        }

        public UpLoadPage moneyTransferClickButton(String lastDigits) {
            val uploadButton = cards.findBy(Condition.text(lastDigits)).$("[data-test-id=action-deposit]");
            uploadButton.click();
            return new UpLoadPage();
        }
    }

