package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCard;
import static ru.netology.data.DataHelper.getSecondCard;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Перевод денег с первой карты на вторую")
    void shouldTransferMoneyFromFirstToSecondCard() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardInfo = getFirstCard();
        val secondCardInfo = getSecondCard();
        int amount = 100;
        val expectedBalanceFirstCard = dashboardPage.getCardsBalance(firstCardInfo) - amount;
        val expectedBalanceSecondCard = dashboardPage.getCardsBalance(secondCardInfo) + amount;
        val uploadPage = dashboardPage.moneyTransferClickButton(secondCardInfo);
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(Integer.parseInt(String.valueOf(amount)), firstCardInfo);
        val actualBalanceFirstCard = dashboardPage2.getCardsBalance(firstCardInfo);
        val actualBalanceSecondCard = dashboardPage2.getCardsBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    @DisplayName("Перевод денег сo второй карты на первую")
    void shouldTransferMoneyFromSecondToFirstCard() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val firstCardInfo = getFirstCard();
        val secondCardInfo = getSecondCard();
        int amount = 100;
        val expectedBalanceFirstCard = dashboardPage.getCardsBalance(firstCardInfo) + amount;
        val expectedBalanceSecondCard = dashboardPage.getCardsBalance(secondCardInfo) - amount;
        val uploadPage = dashboardPage.moneyTransferClickButton(firstCardInfo);
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(Integer.parseInt(String.valueOf(amount)), secondCardInfo);
        val actualBalanceFirstCard = dashboardPage2.getCardsBalance(firstCardInfo);
        val actualBalanceSecondCard = dashboardPage2.getCardsBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
}