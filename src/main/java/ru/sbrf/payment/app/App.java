package ru.sbrf.payment.app;

import ru.sbrf.payment.common.Settings;
import ru.sbrf.payment.db.PaymentDB;
import java.util.Date;

import lombok.*;
import ru.sbrf.payment.db.UsersDB;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class App {
    private Settings settings = new Settings();
    private UserApp user = new UserApp();

    public boolean authUser(String phone, String password, UsersDB usersDB) {
        // Сделать отсылку на сервер phone и password, возвратить результат проверки boolean
        System.out.println("Попытка авторизации пользователя с т." + phone + "...");
        this.user = usersDB.authUser(phone, password);
        boolean result = false;
        if (this.user.getAuth().equals("true")) {
            result = true;
            System.out.println("Успешная авторизация: " + this.user.getUserName() + " (т." + this.user.getPhone() +
                    ") " + this.user.getBalance());
        }
        System.out.println(this.toString());
        return result;
    }

    public PaymentDB payApp(String payeePhone, double amount) {
        // Создать платеж
        Date dateNow = new Date();
        PaymentDB paymentDB = new PaymentDB(this.user.getPhone() + '_' + dateNow.getTime(),
                  dateNow, "1. Создан платеж", this.user.getPhone(), payeePhone, amount);
        System.out.println(paymentDB.getStatus() + " от " + this.user.getUserName() + ":\n" + paymentDB);
        return paymentDB;
    }

}
