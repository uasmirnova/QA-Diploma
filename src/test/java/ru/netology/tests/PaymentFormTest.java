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


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$;
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

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Месяц число больше 12")
    @Test
    public void shouldMonthFieldMore12() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidMonthOver12();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверно указан срок действия карты");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Месяц число 00")
    @Test
    public void shouldMonthFieldNull() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidMonthNull();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверно указан срок действия карты");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Месяц 1 число")
    @Test
    public void shouldMonthField1char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getMonth1char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Месяц символы")
    @Test
    public void shouldMonthFieldSymbols() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidMonthSymbols();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Месяц меньше текущего при текущем годе")
    @Test
    public void shouldMonthFieldLessCurrent() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidMonthLessCurrent();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверно указан срок действия карты");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Месяц пустое")
    @Test
    public void shouldMonthFieldEmpty() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getMonthEmpty();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Год меньше текущего")
    @Test
    public void shouldYearFieldLessCurrent() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidYearLessCurrent();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Истёк срок действия карты");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Год число 00")
    @Test
    public void shouldYearFieldNull() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getYearNull();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Истёк срок действия карты");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Год в формате 4 числа")
    @Test
    public void shouldYearField4char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidYear4char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Год символы")
    @Test
    public void shouldYearFieldSymbols() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidYearSymbols();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Год пустое")
    @Test
    public void shouldYearFieldEmpty() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getYearEmpty();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец с дефисом в середине")
    @Test
    public void shouldHolderFieldWithDashMiddle() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidHolderWithDashMiddle();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец с дефисом в начале")
    @Test
    public void shouldHolderFieldWithDashFirst() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderWithDashFirst();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец с дефисом в конце")
    @Test
    public void shouldHolderFieldWithDashEnd() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderWithDashEnd();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец с пробелом в начале")
    @Test
    public void shouldHolderFieldWithSpaceFirst() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderWithSpaceFirst();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец с пробелом в конце")
    @Test
    public void shouldHolderFieldWithSpaceEnd() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderWithSpaceEnd();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец нижний регистр")
    @Test
    public void shouldHolderFieldLowercase() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderLowercase();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец символами кириллицы")
    @Test
    public void shouldHolderFieldRu() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderRu();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец латиница + цифры")
    @Test
    public void shouldHolderFieldNumbers() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderNumbers();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец латиница + символы")
    @Test
    public void shouldHolderFieldSymbols() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderSymbols();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI") //BUG
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле Владелец пустое")
    @Test
    public void shouldHolderFieldEmpty() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getHolderEmpty();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле CVC/CVV 2 цифры")
    @Test
    public void shouldCVCField2char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidCvc2char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле CVC/CVV 4 цифры")
    @Test
    public void shouldCVCField4char() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidCvc4char();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getSuccessNotification();
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле CVC/CVV символы")
    @Test
    public void shouldCVCFieldSymbols() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getInvalidCvcSymbols();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Неверный формат");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Поле CVC/CVV пустое")
    @Test
    public void shouldCVCFieldEmpty() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getCvcEmpty();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        paymentPage.getInputInvalid("Поле обязательно для заполнения");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Проверка валидации")
    @Story(value = "Все поля пустые")
    @Test
    public void shouldAllFieldsEmpty() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getAllEmpty();
        PaymentPage paymentPage = startPage.paymentButtonClick();
        paymentPage.inputData(CardInfo);
        $$("input__top").find(text("Номер карты")).shouldBe(Condition.visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $$("input__top").find(text("Месяц")).shouldBe(Condition.visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $$("input__top").find(text("Год")).shouldBe(Condition.visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $$("input__top").find(text("Владелец")).shouldBe(Condition.visible).
                shouldHave(text("Поле обязательно для заполнения"));
        $$("input__top").find(text("CVC/CVV")).shouldBe(Condition.visible).
                shouldHave(text("Поле обязательно для заполнения"));
    }







































}
