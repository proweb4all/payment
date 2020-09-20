package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.db.PaymentDB;

public class ServerProc {
    public PaymentDB paymentDB(PaymentDB paymentDB) {
        // Проверка реквизитов платежа по БД платежей и клиентов
        paymentDB.setStatus("2. Платеж проверен по БД");
        System.out.println(paymentDB.getStatus() + ":\n" + paymentDB);
        return paymentDB;
    }
    public PaymentDB paymentOAPI(PaymentDB paymentDB) {
        // Отправка в API сотового оператора
        paymentDB.setStatus("3. Платеж отправлен в API сотового оператора");
        System.out.println(paymentDB.getStatus() + ":\n" + paymentDB);
        // Возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        if (operAPI.procAPI(paymentDB)) {
            paymentDB.setStatus("4. Получено подтверждение платежа от API сотового оператора");
            System.out.println(paymentDB.getStatus() + ":\n" + paymentDB);
        } else {
            paymentDB.setStatus("--- API сотового оператора отклонило платеж");
            System.out.println(paymentDB.getStatus() + ":\n" + paymentDB);
        }
        return paymentDB;
    }
    public void updateBalanceUserApp(double amount, UserApp user) {
        // Корректировка остатка клиента в приложении
        user.setBalance(user.getBalance() - amount);
        System.out.println("6. Корректировка суммы остатка клиента в приложении:\n" + user);
    }

}
