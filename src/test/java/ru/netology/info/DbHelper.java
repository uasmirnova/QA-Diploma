package ru.netology.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class DbHelper {

    //private static final QueryRunner runner = new QueryRunner();

    private DbHelper() {
    }

    //@SneakyThrows
    //private static Connection getConn() {
        //return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    //}

    private static QueryRunner runner;
    private static Connection conn;

    //@SneakyThrows
    public static void getConn() throws SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        //runner = new QueryRunner();
        //conn = DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        System.getProperty("db.url"), "app", "pass"
                );
        ) {
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentEntity {
        private String id;
        private int amount;
        private Timestamp created;
        private String status;
        private String transaction_id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditRequestEntity {
        private String id;
        private String bank_id;
        private Timestamp created;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderEntity {
        private String id;
        private Timestamp created;
        private String credit_id;
        private String payment_id;
    }

    @SneakyThrows
    public static List<PaymentEntity> getPayments() {
        getConn();
        var dbQuery = "SELECT * FROM payment_entity ORDER BY created DESC;";
        ResultSetHandler<List<PaymentEntity>> resultHandler = new BeanListHandler<>(PaymentEntity.class);
        return runner.query(conn, dbQuery, resultHandler);
    }

    @SneakyThrows
    public static List<CreditRequestEntity> getCreditRequests() {
        getConn();
        var dbQuery = "SELECT * FROM credit_request_entity ORDER BY created DESC;";
        ResultSetHandler<List<CreditRequestEntity>> resultHandler = new BeanListHandler<>(CreditRequestEntity.class);
        return runner.query(conn, dbQuery, resultHandler);
    }

    @SneakyThrows
    public static List<OrderEntity> getOrders() {
        getConn();
        var dbQuery = "SELECT * FROM order_entity ORDER BY created DESC;";
        ResultSetHandler<List<OrderEntity>> resultHandler = new BeanListHandler<>(OrderEntity.class);
        return runner.query(conn, dbQuery, resultHandler);
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        getConn();
        var dbStatus = "SELECT status FROM payment_entity;";
        return runner.query(conn, dbStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditStatus() {
        getConn();
        var dbStatus = "SELECT status FROM credit_request_entity;";
        return runner.query(conn, dbStatus, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanDatabase() {
        getConn();
        runner.execute(conn,"DELETE FROM credit_request_entity;");
        runner.execute(conn, "DELETE FROM payment_entity;");
        runner.execute(conn, "DELETE FROM order_entity;");
    }
}
