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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.info.DbHelper.cleanDatabase;

public class PaymentAPITest {

    private static List<DbHelper.PaymentEntity> payments;
    private static List<DbHelper.CreditRequestEntity> credits;
    private static List<DbHelper.OrderEntity> orders;
    private static final String paymentUrl = "/payment";

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeAll
    public static void setUp() {
        cleanDatabase();
    }

    @AfterEach
    public static void tearDown() {
        cleanDatabase();
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с действующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidTestCardApprovedEntityAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        DbHelper.getBody(cardInfo, paymentUrl,200);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();

        assertEquals(1, payments.size());
        assertEquals(0, credits.size());
        assertEquals("APPROVED", DbHelper.getPaymentStatus());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с действующей карты, создание записи в таблице orders")
    @Test
    public void shouldValidTestCardApprovedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardApproved();
        DbHelper.getBody(cardInfo, paymentUrl, 200);
        orders = DbHelper.getOrders();

        assertEquals(1, orders.size());
        assertEquals(DbHelper.getBankIDForPayment(), DbHelper.getPaymentID());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с недействующей карты, создание записи в таблице payment_entity")
    @Test
    public void shouldValidTestCardDeclinedEntityAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        DbHelper.getBody(cardInfo, paymentUrl, 200);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();

        assertEquals(1, payments.size());
        assertEquals(0, credits.size());
        assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Позитивный. Покупка тура с недействующей карты, создание записи в таблице orders")
    @Test
    public void shouldValidTestCardDeclinedOrdersAdded() {
        var cardInfo = DataHelper.getValidCardDeclined();
        DbHelper.getBody(cardInfo, paymentUrl, 200);
        orders = DbHelper.getOrders();

        assertEquals(1, orders.size());
        assertEquals(DbHelper.getBankIDForPayment(), DbHelper.getPaymentID());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Отправка пустого POST запроса платежа")
    @Test
    public void shouldPOSTBodyEmpty() {
        var cardInfo = DataHelper.getAllEmpty();
        DbHelper.getBody(cardInfo, paymentUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Отправка POST запроса платежа с пустым значением number")
    @Test
    public void shouldPOSTNumberEmpty() {
        var cardInfo = DataHelper.getCardEmpty();
        DbHelper.getBody(cardInfo, paymentUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Отправка POST запроса платежа с пустым значением month")
    @Test
    public void shouldPOSTMonthEmpty() {
        var cardInfo = DataHelper.getMonthEmpty();
        DbHelper.getBody(cardInfo, paymentUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Отправка POST запроса платежа с пустым значением year")
    @Test
    public void shouldPOSTYearEmpty() {
        var cardInfo = DataHelper.getYearEmpty();
        DbHelper.getBody(cardInfo, paymentUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Отправка POST запроса платежа с пустым значением holder")
    @Test
    public void shouldPOSTHolderEmpty() {
        var cardInfo = DataHelper.getHolderEmpty();
        DbHelper.getBody(cardInfo, paymentUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }

    @Epic(value = "Тестирование API")
    @Feature(value = "Оплата тура с карты")
    @Story(value = "Отправка POST запроса платежа с пустым значением cvc")
    @Test
    public void shouldPOSTCvcEmpty() {
        var cardInfo = DataHelper.getCvcEmpty();
        DbHelper.getBody(cardInfo, paymentUrl, 400);
        payments = DbHelper.getPayments();
        credits = DbHelper.getCreditRequests();
        orders = DbHelper.getOrders();

        assertEquals(0, payments.size());
        assertEquals(0, credits.size());
        assertEquals(0, orders.size());
    }
}
