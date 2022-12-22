package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.info.DataHelper;
import ru.netology.info.DbHelper;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.info.DbHelper.cleanDatabase;

public class CreditAPITest {

    private static List<DbHelper.PaymentEntity> payments;
    private static List<DbHelper.CreditRequestEntity> credits;
    private static List<DbHelper.OrderEntity> orders;
    private static final String creditUrl = "/credit";

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeAll
    public static void setUp() {
        cleanDatabase();
    }

    @AfterEach
    public void tearDown() {
        cleanDatabase();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с действующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidTestCreditCardApprovedEntityAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        DbHelper.getBody(cardInfo, creditUrl,200);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();

        assertEquals(0, payments.size());
        assertEquals(1, credits.size());
        assertEquals("APPROVED", DbHelper.getCreditStatus());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с действующей карты, создание записи в таблице orders")
    @Test
    public void shouldValidTestCreditCardApprovedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        DbHelper.getBody(cardInfo, creditUrl, 200);
        orders = DbHelper.getOrders();

        assertEquals(1, orders.size());
        assertEquals(DbHelper.getBankIDForCredit(), DbHelper.getCreditID());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с недействующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidTestCreditCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        DbHelper.getBody(cardInfo, creditUrl, 200);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();

        assertEquals(0, payments.size());
        assertEquals(1, credits.size());
        assertEquals("DECLINED", DbHelper.getCreditStatus());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Позитивный. Тур в кредит с недействующей карты, создание записи в таблице orders")
    @Test
    public void shouldValidTestCreditCardDeclinedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        DbHelper.getBody(cardInfo, creditUrl, 200);
        orders = DbHelper.getOrders();

        assertEquals(1, orders.size());
        assertEquals(DbHelper.getBankIDForCredit(), DbHelper.getCreditID());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Отправка пустого POST запроса кредита")
    @Test
    public void shouldCreditPOSTBodyEmpty() {
        var cardInfo = DataHelper.getAllEmpty();
        DbHelper.getBody(cardInfo, creditUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Отправка POST запроса кредита с пустым значением number")
    @Test
    public void shouldCreditPOSTNumberEmpty() {
        var cardInfo = DataHelper.getCardEmpty();
        DbHelper.getBody(cardInfo, creditUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Отправка POST запроса кредита с пустым значением month")
    @Test
    public void shouldCreditPOSTMonthEmpty() {
        var cardInfo = DataHelper.getMonthEmpty();
        DbHelper.getBody(cardInfo, creditUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Отправка POST запроса кредита с пустым значением year")
    @Test
    public void shouldCreditPOSTYearEmpty() {
        var cardInfo = DataHelper.getYearEmpty();
        DbHelper.getBody(cardInfo, creditUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Отправка POST запроса кредита с пустым значением holder")
    @Test
    public void shouldCreditPOSTHolderEmpty() {
        var cardInfo = DataHelper.getHolderEmpty();
        DbHelper.getBody(cardInfo, creditUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Тур в кредит с карты")
    @Story(value = "Отправка POST запроса кредита с пустым значением cvc")
    @Test
    public void shouldCreditPOSTCvcEmpty() {
        var cardInfo = DataHelper.getCvcEmpty();
        DbHelper.getBody(cardInfo, creditUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }
}

