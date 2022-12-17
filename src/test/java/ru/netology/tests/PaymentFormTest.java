package ru.netology.tests;

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
        PaymentPage paymentPage = startPage.paymentButtonClick();
         DataHelper.getRandomCard11char();

    }

}
