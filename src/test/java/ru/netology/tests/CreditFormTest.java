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
import ru.netology.info.DataHelper;
import ru.netology.pages.CreditPage;
import ru.netology.pages.PaymentPage;
import ru.netology.pages.StartPage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.info.DbHelper.cleanDatabase;

public class CreditFormTest {

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDown() {
        cleanDatabase();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с действующей карты (номер с пробелами)")
    @Test
    public void shouldValidCreditTestCardApproved() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidCardApproved();
        CreditPage creditPage = startPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
        //assertEquals("APPROVED", DbHelper.getCreditStatus());
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с действующей карты (номер без пробелов)")
    @Test
    public void shouldValidCreditTestCardApprovedWithoutSpaces() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidCardApprovedWithoutSpaces();
        CreditPage creditPage = startPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getSuccessNotification();
        //assertEquals("APPROVED", DbHelper.getCreditStatus());
    }

    @Epic(value = "Тестирование UI")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с недействующей карты (номер с пробелами)")
    @Test
    public void shouldValidCreditTestCardDeclined() {
        StartPage startPage = new StartPage();
        var CardInfo = DataHelper.getValidCardDeclined();
        CreditPage creditPage = startPage.creditButtonClick();
        creditPage.inputData(CardInfo);
        creditPage.getErrorNotification();
        //assertEquals("DECLINED", DbHelper.getCreditStatus());
    }
}
