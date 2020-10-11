package ru.sbrf.payment;

import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.ServerSber;
import ru.sbrf.payment.server.ServerVTB;
import ru.sbrf.payment.server.ServerDefault;

public class Main {
    public static void main(String[] args) {
        WebApp app = new WebApp();
        ServerSber serverSber = new ServerSber(); // Можно закомментировать, app будет запускаться, но без серверного функционала
//        ServerVTB serverVTB = new ServerVTB(); // Пример другого сервера, можно подключить его
//        ServerDefault serverProcDefault = new ServerDefault(); // Пример сервера с дефолтным (никаким) функционалом
        app.runApp();
    }
}
