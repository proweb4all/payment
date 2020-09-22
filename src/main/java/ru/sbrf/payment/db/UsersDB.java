package ru.sbrf.payment.db;

import java.util.HashMap;

import ru.sbrf.payment.app.StatusAuth;
import ru.sbrf.payment.app.UserApp;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UsersDB {
    HashMap<String, UserDB> usersDB = new HashMap<>();
    public void init() {
        usersDB.put("0123456789", new UserDB("0123", "0123456789", "Ваня Ветров", 100.0));
        usersDB.put("1123456789", new UserDB("1123", "1123456789", "Клава Форточкина", 200.0));
        usersDB.put("2123456789", new UserDB("2123", "2123456789", "Никифор Ляпис-Трубецкой", 300.0));
    }
    public UserApp authUser(String phone, String password) {
        UserDB user = usersDB.get(phone);
        UserApp userFromDB;
        if (user != null) {
            if (user.getPassword().equals(password)) {
                userFromDB = new UserApp(StatusAuth.A1, phone, user.getUserName(), user.getBalance());
            } else {
                userFromDB = new UserApp(StatusAuth.A3);
            }
        } else {
            userFromDB = new UserApp(StatusAuth.A2);
        }
        return userFromDB;
    }
    public PaymentDB paymentConfirm(PaymentDB paymentDB) {
        // Сохранение платежа в БД платежей, корректировка остатка в БД клиентов
        UserDB user = usersDB.get(paymentDB.getPayerPhone());
        user.setBalance(user.getBalance() - paymentDB.getAmount());
        paymentDB.setPaymentStatus(PaymentStatus.PS5);
        System.out.println(paymentDB.getPaymentStatus().getDescr() + ":\n" + paymentDB);
        System.out.println("usersDB: " + usersDB);
        return paymentDB;
    }
}
