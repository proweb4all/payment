package ru.sbrf.payment;

import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.ServerProc;

public class Main {
    public static void main(String[] args) {
        WebApp app = new WebApp();
        ServerProc serverProc = new ServerProc();
        app.runApp();
    }
}
