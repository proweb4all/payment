package ru.sbrf.payment.app;

import ru.sbrf.payment.common.Settings;
import ru.sbrf.payment.server.Payment;
import java.util.Date;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class App {
    private Settings settings = new Settings();
    private UserApp user = new UserApp();

    public String authUser(String phone, String password) {
        // Сделать отсылку на сервер phone и password, возвратить результат проверки boolean
        boolean res = true;
        String result = "Ошибка авторизации";
        if (res) {
            String name = "Ваня Ветров";
            this.user.setPhone(phone);
            double balance = 100.0;
            this.user.setAuth(String.valueOf(res));
            this.user.setUserName(name);
            this.user.setBalance(balance);
            result = "Успешная авторизация: " + this.user.getUserName() + " (т." + this.user.getPhone() +
                    ") " + this.user.getBalance() + " (" + this.user.getAuth() + ")" + " (" + password + ")";
        }
        return result;
    }

    public Payment payApp(String payeePhone, double amount) {
        if (this.user.getAuth().equals("true")) {
            // Создать платеж
            Date dateNow = new Date();
            Payment payment = new Payment(this.user.getPhone() + '_' + dateNow.getTime(),
                    dateNow, "1. Создан платеж", this.user.getPhone(), payeePhone, amount);
            System.out.println(payment.getStatus() + " от " + this.user.getUserName() + ":\n" + payment);
            return payment;
        } else {
            Payment payment = new Payment();
            System.out.println("Вы не авторизованы в приложении. Без этого отправка платежей невозможна.\n" + payment);
            return payment;
        }
    }


}
