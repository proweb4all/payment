package ru.sbrf.payment.server;

import lombok.extern.slf4j.Slf4j;
import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.db.PaymentsDB;
import ru.sbrf.payment.db.UsersDB;
import java.util.Optional;
import lombok.*;
@ToString
@Getter
@Slf4j

public class ServerProc implements Server{
    public static Optional<ServerProc> serverLink = Optional.empty();
    private UsersDB usersDB = new UsersDB();
    private PaymentsDB paymentsDB = new PaymentsDB();

    public ServerProc() {
        serverLink = Optional.of(this);
        usersDB.init();
        paymentsDB.init();
    }

    public UserApp authUserServer(String phone, String password) {
        log.info("Попытка авторизации пользователя с т." + phone + "...");
        return usersDB.authUser(phone, password);
    }

    public Payment payServer(Payment payment) {
        // Проведение платежа на сервере
        if (checkPayment(payment.getId())) {
            payment.setPaymentStatus(PaymentStatus.PS2);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS12);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        }
        if (paymentToAPI(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS3);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS13);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        }
        if (usersDB.paymentToUsersDB(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS4);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS14);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        }
        if (updatePaymentsDB(payment)) {
            payment.setPaymentStatus(PaymentStatus.PS5);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        } else {
            payment.setPaymentStatus(PaymentStatus.PS15);
            System.out.println(payment.getPaymentStatus().getDescr());
            log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
        }
        return payment;
    }

    boolean checkPayment(String paymentID) {
        // Проверка реквизитов платежа по БД платежей
        return paymentsDB.checkPaymentID(paymentID);
    }
    boolean paymentToAPI(Payment payment) {
        // Отправка и возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        return operAPI.procAPI(payment);
    }
    boolean updatePaymentsDB(Payment payment) {
        // Запись платежа в БД платежей
        return paymentsDB.addPaymentToDB(payment);
    }

}
