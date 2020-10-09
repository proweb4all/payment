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
            setStatusPaymentAndLogging(payment, PaymentStatus.PS2);
        } else {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS12);
        }
        if (paymentToAPI(payment)) {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS3);
        } else {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS13);
        }
        if (usersDB.paymentToUsersDB(payment)) {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS4);
        } else {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS14);
        }
        if (updatePaymentsDB(payment)) {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS5);
        } else {
            setStatusPaymentAndLogging(payment, PaymentStatus.PS15);
        }
        return payment;
    }

    public static void setStatusPaymentAndLogging(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        System.out.println(payment.getPaymentStatus().getDescr());
        log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
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
