package ru.sbrf.payment.app;

import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
//import ru.sbrf.payment.db.PaymentsDB;
import ru.sbrf.payment.server.ServerProc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class WebAppTest {

    WebApp app = new WebApp();
    ServerProc serverProc = new ServerProc();
    Payment payment;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        app.authUserApp(ServerProc.serverLink.get().authUserServer("9102222222", "222222"));

        Date dateNow = new Date();
        payment = new Payment(Payment.createPaymentID(app.getUser().getPhone(), dateNow),
                app.getUser().getPhone(), app.getUser().getAccount(), dateNow, PaymentStatus.PS1, "9101234567", 100.0);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void authUser() {
        assertTrue(app.authUserApp(ServerProc.serverLink.get().authUserServer("9102222222", "222222")));
    }

    @org.junit.jupiter.api.Test
    void payApp() {
        assertTrue(app.payApp("9101234567", 100.0));
    }

    @org.junit.jupiter.api.Test
    void updateBalanceUserApp() {
        assertTrue(app.updateBalanceUserApp(100.0, app.getUser()));
    }
}