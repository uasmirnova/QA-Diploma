package ru.netology.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.pages.PaymentPage;
import ru.netology.pages.StartPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DbHelper.cleanDatabase;

public class PaymentFormTest {

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    //@AfterAll
    //static void tearDown() {
        //cleanDatabase();
    //}

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с действующей карты (номер с пробелами)")
    @Test
    public void shouldValidTestCardApproved() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidCardApproved();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
        //assertEquals("APPROVED", DbHelper.getPaymentStatus());
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с действующей карты (номер без пробелов)")
    @Test
    public void shouldValidTestCardApprovedWithoutSpaces() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidCardApprovedWithoutSpaces();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
        //assertEquals("APPROVED", DbHelper.getPaymentStatus());
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с недействующей карты (номер с пробелами)")
    @Test
    public void shouldValidTestCardDeclined() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidCardDeclined();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getErrorNotification();
        //assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Номер карты 11 цифр")
    @Test
    public void shouldNumberField11char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getRandomCard11char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Номер карты 16 случайных цифр")
    @Test
    public void shouldNumberField16char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getRandomCard16char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getErrorNotification();
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Номер карты 19 случайных цифр")
    @Test
    public void shouldNumberField19char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getRandomCard19char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getErrorNotification();
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Номер карты 20 случайных цифр")
    @Test
    public void shouldNumberField20char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getRandomCard20char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getErrorNotification();
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Номер карты символы")
    @Test
    public void shouldNumberFieldSymbols() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getRandomCardSymbols();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Номер карты пустое")
    @Test
    public void shouldNumberFieldEmpty() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getCardEmpty();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Поле обязательно для заполнения");
    }



}
