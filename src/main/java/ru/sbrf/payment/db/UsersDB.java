package ru.sbrf.payment.db;

import java.util.HashMap;
import ru.sbrf.payment.app.AuthStatus;
import ru.sbrf.payment.app.UserApp;
import lombok.*;
@ToString
@Getter

public class UsersDB implements IUsersDB{
    private final HashMap<String, UserRecord> usersDB = new HashMap<>();

    @Override
    public void init() {}

    @Override
    public UserApp authUser(String phone, String password) {
        UserRecord user = usersDB.get(phone);
        UserApp userApp;
        if (user != null) {
            if (user.getPassword().equals(password)) {
                userApp = new UserApp(AuthStatus.A1, phone, user.getUserName(), user.getAccount(), user.getBalance());
            } else {
                userApp = new UserApp(AuthStatus.A3);
            }
        } else {
            userApp = new UserApp(AuthStatus.A2);
        }
        return userApp;
    }

    @Override
    public boolean paymentToUsersDB(Payment payment) {
        // Корректировка остатка в БД клиентов
        UserRecord user = usersDB.get(payment.getPayerPhone());
        if (user != null) {
            user.setBalance(user.getBalance() - payment.getAmount());
            return true;
        } else {
            return false;
        }
    }
}