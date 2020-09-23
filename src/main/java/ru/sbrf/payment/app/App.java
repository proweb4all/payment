package ru.sbrf.payment.app;

import java.util.Date;
import ru.sbrf.payment.common.Settings;
import ru.sbrf.payment.db.PaymentDB;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.db.UsersDB;
import ru.sbrf.payment.server.OperatorAPI;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class App {
    private Settings settings = new Settings();
    private UserApp user = new UserApp();

    public boolean authUser(String phone, String password, UsersDB usersDB) {
        // Сделать отсылку на сервер phone и password, возвратить результат проверки boolean
        //System.out.println("Попытка авторизации пользователя с т." + phone + "...");
        this.user = usersDB.authUser(phone, password);
        boolean result = (this.user.getAuthEnum() == StatusAuth.A1);
        if (result) {
            System.out.println(this.user.getAuthEnum().getDescr() + " " + this.user.getUserName() + " (т." + this.user.getPhone() + ") " + this.user.getBalance());
        } else {
            System.out.println(this.user.getAuthEnum().getDescr());
        }
        return result;
    }

    public boolean payApp(String payeePhone, double amount, UsersDB usersDB) {
        // Создать платеж
        Date dateNow = new Date();
        PaymentDB paymentDB = new PaymentDB(this.user.getPhone() + '_' + dateNow.getTime(),
                  dateNow, PaymentStatus.PS1, this.user.getPhone(), payeePhone, amount);
        System.out.println(paymentDB.getPaymentStatus().getDescr() + " от " + this.user.getUserName());// + ":\n" + paymentDB);
        if (checkPaymentDB()) {
            paymentDB.setPaymentStatus(PaymentStatus.PS2);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
        } else {
            paymentDB.setPaymentStatus(PaymentStatus.PS12);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
            return false;
        }
        if (paymentToAPI(paymentDB)) {
            paymentDB.setPaymentStatus(PaymentStatus.PS3);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
        } else {
            paymentDB.setPaymentStatus(PaymentStatus.PS13);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
            return false;
        }
        if (usersDB.paymentToUsersDB(paymentDB)) {
            paymentDB.setPaymentStatus(PaymentStatus.PS4);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
        } else {
            paymentDB.setPaymentStatus(PaymentStatus.PS14);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
            //return false;
        }
        if (updatePaymentsDB(paymentDB)) {
           paymentDB.setPaymentStatus(PaymentStatus.PS5);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
        } else {
            paymentDB.setPaymentStatus(PaymentStatus.PS15);
            System.out.println(paymentDB.getPaymentStatus().getDescr());// + ":\n" + paymentDB);
            //return false;
        }
        if (updateBalanceUserApp(paymentDB.getAmount(), this.getUser())) {
            System.out.println(PaymentStatus.PS6.getDescr());// + ":\n" + user);
        } else {
            System.out.println(PaymentStatus.PS16.getDescr());// + ":\n" + user);
        }
        System.out.printf("=== Платеж №%s от %s (т.%s) пользователю т.%s на сумму %.2fруб. успешно проведен! ===\n",
                   paymentDB.getId(), this.getUser().getUserName(), paymentDB.getPayerPhone(), paymentDB.getPayeePhone(), paymentDB.getAmount());
        return true;
    }

    boolean checkPaymentDB() {
        // Проверка реквизитов платежа по БД платежей и клиентов
        return true;
//        return false;
    }
    boolean paymentToAPI(PaymentDB paymentDB) {
        // Отправка и возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        return operAPI.procAPI(paymentDB);
    }
    boolean updatePaymentsDB(PaymentDB paymentDB) {
        // Запись платежа в БД платежей
        return true;
//        return false;
    }
    boolean updateBalanceUserApp(double amount, UserApp user) {
        // Корректировка остатка клиента в приложении
        user.setBalance(user.getBalance() - amount);
        return true;
//        return false;
    }

}
