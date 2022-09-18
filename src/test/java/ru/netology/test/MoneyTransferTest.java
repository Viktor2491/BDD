package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    int begBalance1;
    int begBalance2;
    int endBalance1;
    int endBalance2;
    int amount;
    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        begBalance1 = dashboardPage.getCardsBalance(dashboardPage.card1);
        begBalance2 = dashboardPage.getCardsBalance(dashboardPage.card2);

    }

    @Test
    @DisplayName("Перевод денег сo второй карты на первую")
    void shouldTransferMoneyFromSecondToFirstCard() {
        amount = 100;
        val upLoadPage = dashboardPage.moneyTransferClickButton(dashboardPage.card1);
        val cardNumber = DataHelper.getSecondCard().getCardNumber();
        val dashboardPage2 = upLoadPage.shouldTransferMoneyBetweenCards(Integer.toString(amount), cardNumber);
        endBalance1 = dashboardPage2.getCardsBalance(dashboardPage.card1);
        endBalance2 = dashboardPage2.getCardsBalance(dashboardPage.card2);
        assertEquals(begBalance1 + amount, endBalance1);
        assertEquals(begBalance2 - amount, endBalance2);
    }


    @Test
    @DisplayName("Перевод денег с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecondCard() {
        amount = 100;
        val upLoadPage = dashboardPage.moneyTransferClickButton(dashboardPage.card2);
        val cardNumber = DataHelper.getFirstCard().getCardNumber();
        val dashboardPage2 = upLoadPage.shouldTransferMoneyBetweenCards(Integer.toString(amount), cardNumber);
        endBalance1 = dashboardPage2.getCardsBalance(dashboardPage2.card1);
        endBalance2 = dashboardPage2.getCardsBalance(dashboardPage2.card2);
        assertEquals(begBalance1 - amount, endBalance1);
        assertEquals(begBalance2 + amount, endBalance2);
    }

    @Test
    @DisplayName("перевод свыше баланса карты")
    void shouldNotTransferMoneyOverTheCardBalance() {
        amount = begBalance1 + 10;
        val upLoadPage = dashboardPage.moneyTransferClickButton(dashboardPage.card2);
        val cardNumber = DataHelper.getFirstCard().getCardNumber();
        upLoadPage.shouldNotTransferMoneyBetweenCards(Integer.toString(amount), cardNumber);
    }
}