package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.*;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import lombok.*;
@ToString
@Getter
@Slf4j

public class ServerVTB extends BaseServer{
    private PaymentsDBVTB paymentsDBVTB = new PaymentsDBVTB();
    private UsersDBVTB usersDBVTB = new UsersDBVTB();

    public ServerVTB() {
        serverLink = Optional.of(this);
        setNameServer("ServerVTB");
        usersDBVTB.init();
        paymentsDBVTB.init();
    }

    @Override
    public UserApp authUserServer(String phone, String password) {
        log.info("Попытка авторизации пользователя с т." + phone + "...");
        return usersDBVTB.authUser(phone, password);
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
        if (usersDBVTB.paymentToUsersDB(payment)) {
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
        System.out.println(getNameServer() + ": - " + payment.getPaymentStatus().getDescr());
        log.info(payment.getPaymentStatus().getDescr());// + ":\n" + payment);
    }
    boolean checkPayment(String paymentID) {
        // Проверка реквизитов платежа по БД платежей
        return paymentsDBVTB.checkPaymentID(paymentID);
    }
    boolean paymentToAPI(Payment payment) {
        // Отправка и возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        return operAPI.procAPI(payment);
    }
    boolean updatePaymentsDB(Payment payment) {
        // Запись платежа в БД платежей
        return paymentsDBVTB.addPaymentToDB(payment);
    }

}
