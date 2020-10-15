package ru.sbrf.payment.db;

import ru.sbrf.payment.app.UserApp;

public interface IUsersDB {
    void init();
    boolean paymentToUsersDB(Payment payment);
    UserApp authUser(String phone, String password);
}
