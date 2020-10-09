package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.Payment;

public interface Server {
    UserApp authUserServer(String phone, String password);
    Payment payServer(Payment payment);
}
