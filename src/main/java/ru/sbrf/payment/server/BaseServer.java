package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import java.util.Optional;

public class BaseServer implements IServer {
    public static Optional<BaseServer> serverLink = Optional.empty();

    @Override
    public UserApp authUserServer(String phone, String password) {
        System.out.println("BaseServer: - авторизация");
        return new UserApp();
    }

    @Override
    public Payment payServer(Payment payment) {
        return payment;
    }

    public void setPaymentStatusAndLogging(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        System.out.println("BaseServer: - " + payment.getPaymentStatus().getDescr());
    }
}