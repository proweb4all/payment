package ru.sbrf.payment.app;

import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.db.PaymentsDB;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    App app = new App();
    Payment payment;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        app.init();
        app.authUser("9102222222", "222222", app.getUsersDB());
        Date dateNow = new Date();
        payment = new Payment(PaymentsDB.createPaymentID(app.getUser().getPhone(), dateNow),
                app.getUser().getPhone(), app.getUser().getAccount(), dateNow, PaymentStatus.PS1, "9101234567", 100.0);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

//    @org.junit.jupiter.api.Test
//    void init() {
//    }

    @org.junit.jupiter.api.Test
    void authUser() {
//        assertTrue(app.authUser("9102222222", "222222", app.getUsersDB()));
    }

    @org.junit.jupiter.api.Test
    void payApp() {
        assertTrue(app.payApp("9101234567", 100.0, app.getUsersDB()));
    }

    @org.junit.jupiter.api.Test
    void checkPayment() {
        assertTrue(app.checkPayment(payment.getId()));
    }

    @org.junit.jupiter.api.Test
    void paymentToAPI() {
        assertTrue(app.paymentToAPI(this.payment));
    }

    @org.junit.jupiter.api.Test
    void updatePaymentsDB() {
        assertTrue(app.updatePaymentsDB(this.payment));
    }

    @org.junit.jupiter.api.Test
    void updateBalanceUserApp() {
        assertTrue(app.updateBalanceUserApp(100.0, app.getUser()));
    }
}