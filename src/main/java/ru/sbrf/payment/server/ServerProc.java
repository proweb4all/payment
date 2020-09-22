package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.PaymentDB;
import ru.sbrf.payment.db.PaymentStatus;

public class ServerProc {
    public PaymentDB paymentDB(PaymentDB paymentDB) {
        // Проверка реквизитов платежа по БД платежей и клиентов
        paymentDB.setPaymentStatus(PaymentStatus.PS2);
        System.out.println(paymentDB.getPaymentStatus().getDescr() + ":\n" + paymentDB);
        return paymentDB;
    }
    public PaymentDB paymentOAPI(PaymentDB paymentDB) {
        // Отправка в API сотового оператора
        paymentDB.setPaymentStatus(PaymentStatus.PS3);
        System.out.println(paymentDB.getPaymentStatus().getDescr() + ":\n" + paymentDB);
        // Возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        if (operAPI.procAPI(paymentDB)) {
            paymentDB.setPaymentStatus(PaymentStatus.PS4);
            System.out.println(paymentDB.getPaymentStatus().getDescr() + ":\n" + paymentDB);
        } else {
            paymentDB.setPaymentStatus(PaymentStatus.PS12);
            System.out.println(paymentDB.getPaymentStatus().getDescr() + ":\n" + paymentDB);
        }
        return paymentDB;
    }
    public void updateBalanceUserApp(double amount, UserApp user) {
        // Корректировка остатка клиента в приложении
        user.setBalance(user.getBalance() - amount);
        System.out.println("6. Корректировка суммы остатка клиента в приложении:\n" + user);
    }

}
