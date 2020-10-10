package ru.sbrf.payment;

import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.ServerProc;
import ru.sbrf.payment.server.ServerProc1;
import ru.sbrf.payment.server.ServerProcDefault;

public class Main {
    public static void main(String[] args) {
        WebApp app = new WebApp();
        ServerProc serverProc = new ServerProc(); // Можно закомментировать, app будет запускаться, но без серверного функционала
//        ServerProc1 serverProc1 = new ServerProc1(); // Пример другого сервера, можно подключить его
//        ServerProcDefault serverProcDefault = new ServerProcDefault(); // Пример сервера с дефолтным (никаким) функционалом
        app.runApp();
    }
}
