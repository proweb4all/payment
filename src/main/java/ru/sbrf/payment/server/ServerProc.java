package ru.sbrf.payment.server;

import ru.sbrf.payment.app.UserApp;

public class ServerProc {
    public Payment paymentDB(Payment payment) {
        // Проверка реквизитов платежа по БД платежей и клиентов
        payment.setStatus("2. Платеж проверен по БД");
        System.out.println(payment.getStatus() + ":\n" + payment);
        return payment;
    }
    public Payment paymentOAPI(Payment payment) {
        // Отправка в API сотового оператора
        payment.setStatus("3. Платеж отправлен в API сотового оператора");
        System.out.println(payment.getStatus() + ":\n" + payment);
        // Возврат из API сотового оператора
        OperatorAPI operAPI = new OperatorAPI();
        if (operAPI.procAPI(payment)) {
            payment.setStatus("4. Получено подтверждение платежа от API сотового оператора");
            System.out.println(payment.getStatus() + ":\n" + payment);
        } else {
            payment.setStatus("--- API сотового оператора отклонило платеж");
            System.out.println(payment.getStatus() + ":\n" + payment);
        }
        return payment;
    }
    public Payment paymentConfirm(Payment payment) {
        // Сохранение платежа в БД платежей, корректировка остатка в БД клиентов
        payment.setStatus("5. Платеж проведен в БД");
        System.out.println(payment.getStatus() + ":\n" + payment);
        return payment;
    }
    public void updateBalanceUserApp(double amount, UserApp user) {
        // Корректировка остатка клиента в приложении
        user.setBalance(user.getBalance() - amount);
        System.out.println("+++ Корректировка суммы остатка клиента в приложении:\n" + user);
    }

}
