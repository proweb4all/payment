package ru.sbrf.payment;

import ru.sbrf.payment.app.App;
import ru.sbrf.payment.db.PaymentDB;
import ru.sbrf.payment.db.UsersDB;
import ru.sbrf.payment.server.ServerProc;

public class Main {
    public static void main(String[] args) {
        System.out.println("Инициалитизация БД:");
        UsersDB usersDB = new UsersDB();
        usersDB.init();
        System.out.println(usersDB.toString());
        App app = new App();
        System.out.println("Запустили приложение:\n" + app.toString());
        if (app.authUser("1123456789", "1123", usersDB)) {
            ServerProc serverProc = new ServerProc();
            PaymentDB paymentDB = serverProc.paymentOAPI(serverProc.paymentDB(app.payApp("1122334455", 50.0)));
            if (paymentDB.getStatus().charAt(0) == '4') {
                paymentDB = usersDB.paymentConfirm(paymentDB);
                serverProc.updateBalanceUserApp(paymentDB.getAmount(), app.getUser());
                System.out.println(app.toString());
                System.out.printf("=== Платеж №%s от %s (т.%s) пользователю т.%s на сумму %.2fруб. успешно проведен! ===\n",
                        paymentDB.getId(), app.getUser().getUserName(), paymentDB.getPayerPhone(), paymentDB.getPayeePhone(), paymentDB.getAmount());
                System.out.printf("");
            } else {
                System.out.println("=== Платеж отклонен мобильным оператором! ===");
            }
        } else {
            System.out.println("--- Вы не авторизованы в приложении! Без этого отправка платежей невозможна. ---");
        }
    }
}
