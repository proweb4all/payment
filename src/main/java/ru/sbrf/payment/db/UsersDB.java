package ru.sbrf.payment.db;

import java.util.HashMap;
import ru.sbrf.payment.app.StatusAuth;
import ru.sbrf.payment.app.UserApp;
import lombok.*;

//@ToString
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor

public class UsersDB {
    private HashMap<String, UserRecord> usersDB = new HashMap<>();

    public void init() {
        usersDB.put("9101111111", new UserRecord("111111", "9101111111", "Ваня Ветров", "XXXXX810X53001111111", 300.0));
        usersDB.put("9102222222", new UserRecord("222222", "9102222222", "Клава Форточкина", "XXXXX810X53002222222", 400.0));
        usersDB.put("9103333333", new UserRecord("333333", "9103333333", "Никифор Ляпис-Трубецкой", "XXXXX810X53003333333", 500.0));
    }

    public UserApp authUser(String phone, String password) {
        UserRecord user = usersDB.get(phone);
        UserApp userFromDB;
        if (user != null) {
            if (user.getPassword().equals(password)) {
                userFromDB = new UserApp(StatusAuth.A1, phone, user.getUserName(), user.getAccount(), user.getBalance());
            } else {
                userFromDB = new UserApp(StatusAuth.A3);
            }
        } else {
            userFromDB = new UserApp(StatusAuth.A2);
        }
        return userFromDB;
    }

    public boolean paymentToUsersDB(Payment payment) {
        // Корректировка остатка в БД клиентов
        UserRecord user = usersDB.get(payment.getPayerPhone());
        user.setBalance(user.getBalance() - payment.getAmount());
        return true;
    }
}
