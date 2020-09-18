package ru.sbrf.payment;

import ru.sbrf.payment.app.App;
import ru.sbrf.payment.server.Payment;
import ru.sbrf.payment.server.ServerProc;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        System.out.println("Запустили приложение:\n" + app.toString());
        System.out.println(app.authUser("0123456789", "password"));
        System.out.println(app.toString());
        ServerProc serverProc = new ServerProc();
        Payment payment = serverProc.paymentOAPI(serverProc.paymentDB(app.payApp("1122334455", 50.0)));
        if (payment.getStatus().charAt(0) == '4') {
            payment = serverProc.paymentConfirm(payment);
            serverProc.updateBalanceUserApp(payment.getAmount(), app.getUser());
            System.out.println(app.toString());
            System.out.println("=== Платеж успешно проведен! ===");
        } else {
            System.out.println("=== Платеж отклонен мобильным оператором! ===");
        }
    }
}
