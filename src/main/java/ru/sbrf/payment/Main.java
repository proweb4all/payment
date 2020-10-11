package ru.sbrf.payment;

import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.ServerBank1;

public class Main {
    public static void main(String[] args) {
        WebApp app = new WebApp();
        ServerBank1 serverBank1 = new ServerBank1(); // Можно закомментировать, app будет запускаться, но без серверного функционала
//        ServerBank2 serverVTB = new ServerBank2(); // Пример другого сервера, можно подключить его
//        ServerDefault serverProcDefault = new ServerDefault(); // Пример сервера с дефолтным (никаким) функционалом
        app.runApp();
    }
}
