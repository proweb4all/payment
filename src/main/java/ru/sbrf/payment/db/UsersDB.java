package ru.sbrf.payment.db;

import java.util.HashMap;
import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.common.User;
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
        UserApp userFromDB = new UserApp("false");
//        User userF = user;
//        System.out.println("Результаты поиска: " + userF);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                userFromDB = new UserApp("true", phone, user.getUserName(), user.getBalance());
            } else {
                System.out.println("Авторизация не удалась: Неверный пароль.");
            }
        } else {
            System.out.println("Авторизация не удалась: Пользователь не найден.");
        }
        return userFromDB;
    }
    public PaymentDB paymentConfirm(PaymentDB paymentDB) {
        // Сохранение платежа в БД платежей, корректировка остатка в БД клиентов
        UserDB user = usersDB.get(paymentDB.getPayerPhone());
        user.setBalance(user.getBalance() - paymentDB.getAmount());
        paymentDB.setStatus("5. Платеж проведен в БД");
        System.out.println(paymentDB.getStatus() + ":\n" + paymentDB);
        System.out.println("usersDB: " + usersDB);
        return paymentDB;
    }
}
