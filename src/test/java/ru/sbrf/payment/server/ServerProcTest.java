package ru.sbrf.payment.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbrf.payment.app.AuthStatus;
import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ServerProcTest {
    WebApp app = new WebApp();
    ServerProc serverProc = new ServerProc();
    Payment payment;

    @BeforeEach
    void setUp() {
        app.authUserApp(ServerProc.serverLink.get().authUserServer("9102222222", "222222"));

        Date dateNow = new Date();
        payment = new Payment(Payment.createPaymentID(app.getUser().getPhone(), dateNow),
                app.getUser().getPhone(), app.getUser().getAccount(), dateNow, PaymentStatus.PS1, "9101234567", 100.0);

    }

    @Test
    void authUserServer() {
        assertTrue(serverProc.authUserServer("9102222222", "222222").getAuthStatus() == AuthStatus.A1);
    }

    @Test
    void payServer() {
        assertTrue(serverProc.payServer(payment).getPaymentStatus() == PaymentStatus.PS5);
    }

    @Test
    void checkPayment() {
        assertTrue(serverProc.checkPayment(payment.getId()));
    }

    @Test
    void paymentToAPI() {
        assertTrue(serverProc.paymentToAPI(payment));
    }

    @Test
    void updatePaymentsDB() {
        assertTrue(serverProc.updatePaymentsDB(payment));
    }
}