package ru.sbrf.payment.app;

import java.util.Date;
import ru.sbrf.payment.common.Settings;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.db.PaymentsDB;
import ru.sbrf.payment.db.UsersDB;
import ru.sbrf.payment.server.OperatorAPI;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
//@AllArgsConstructor

public class App {
    private Settings settings = new Settings();
    private UserApp user = new UserApp();
    private UsersDB usersDB = new UsersDB();
    private PaymentsDB paymentsDB = new PaymentsDB();

    public void init() {
        usersDB.init();
        paymentsDB.init();
    }

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
        Payment payment = new Payment(PaymentsDB.createPaymentID(this.user.getPhone(), dateNow),
                this.user.getPhone(), this.user.getAccount(), dateNow, PaymentStatus.PS1, payeePhone, amount);
        System.out.println(payment.getPaymentStatus().getDescr() + " от " + this.user.getUserName());// + ":\n" + payment);
        if (checkPayment(payment.getId())) {
            payment.setPaymentStatus(PaymentStatus.PS2);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS12);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            return false;
        }
        if (paymentToAPI(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS3);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS13);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            return false;
        }
        if (usersDB.paymentToUsersDB(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS4);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS14);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            //return false;
        }
        if (updatePaymentsDB(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS5);
            paymentsDB.addPaymentToDB(payment);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS15);
            System.out.println(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
            //return false;
        }
        if (updateBalanceUserApp(payment.getAmount(), this.getUser())) {
            System.out.println(PaymentStatus.PS6.getDescr());// + ":\n" + user);
        } else {
            System.out.println(PaymentStatus.PS16.getDescr());// + ":\n" + user);
        }
        System.out.printf("=== Успешно проведен платеж №%s от %s (т.%s) пользователю т.%s на сумму %.2fруб. ===\n",
                   payment.getId(), this.getUser().getUserName(), payment.getPayerPhone(), payment.getPayeePhone(), payment.getAmount());
        return true;
    }

    boolean checkPayment(String paymentID) {
        // Проверка реквизитов платежа по БД платежей
        return paymentsDB.checkPaymentID(paymentID);
//        return false;
    }
    boolean paymentToAPI(Payment payment) {
        // Отправка и возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        return operAPI.procAPI(payment);
    }
    boolean updatePaymentsDB(Payment payment) {
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
