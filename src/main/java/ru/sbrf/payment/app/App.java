package ru.sbrf.payment.app;

import ru.sbrf.payment.common.Settings;
import ru.sbrf.payment.db.PaymentDB;
import java.util.Date;

import lombok.*;
import ru.sbrf.payment.db.PaymentStatus;
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
        boolean result = (this.user.getAuthEnum() == StatusAuth.A1);
        if (result) {
            System.out.println(this.user.getAuthEnum().getDescr() + " " + this.user.getUserName() + " (т." + this.user.getPhone() + ") " + this.user.getBalance());
        } else {
            System.out.println(this.user.getAuthEnum().getDescr());
        }
        return result;
    }

    public PaymentDB payApp(String payeePhone, double amount) {
        // Создать платеж
        Date dateNow = new Date();
        PaymentDB paymentDB = new PaymentDB(this.user.getPhone() + '_' + dateNow.getTime(),
                  dateNow, PaymentStatus.PS1, this.user.getPhone(), payeePhone, amount);
        System.out.println(paymentDB.getPaymentStatus().getDescr() + " от " + this.user.getUserName() + ":\n" + paymentDB);
        return paymentDB;
    }

}
