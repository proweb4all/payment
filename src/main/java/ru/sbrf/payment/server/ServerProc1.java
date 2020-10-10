package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.Payment;
import ru.sbrf.payment.db.PaymentStatus;
import ru.sbrf.payment.db.PaymentsDB;
import ru.sbrf.payment.db.UsersDB;
import java.util.Optional;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
@ToString
@Getter
@Slf4j

public class ServerProc1 extends BaseServer{
    private final UsersDB usersDB = new UsersDB();
    private final PaymentsDB paymentsDB = new PaymentsDB();

    public ServerProc1() {
        serverLink = Optional.of(this);
        usersDB.init();
        paymentsDB.init();
    }

    @Override
    public UserApp authUserServer(String phone, String password) {
        log.info("Попытка авторизации пользователя с т." + phone + "...");
        return usersDB.authUser(phone, password);
    }

    @Override
    public Payment payServer(Payment payment) {
        // Проведение платежа на сервере
        if (checkPayment(payment.getId())) {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS2);
        } else {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS12);
        }
        if (paymentToAPI(payment)) {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS3);
        } else {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS13);
        }
        if (usersDB.paymentToUsersDB(payment)) {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS4);
        } else {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS14);
        }
        if (updatePaymentsDB(payment)) {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS5);
        } else {
            setPaymentStatusAndLogging(payment, PaymentStatus.PS15);
        }
        return payment;
    }

    @Override
    public void setPaymentStatusAndLogging(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus);
        System.out.println("ServerProc1: - " + payment.getPaymentStatus().getDescr());
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
