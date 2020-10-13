package ru.sbrf.payment;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.ServerBank1;
//import ru.sbrf.payment.server.ServerBank2;
//import ru.sbrf.payment.server.ServerDefault;

public class Main {
    public static void main(String[] args) {

//        // Обычная реализация
        WebApp app = new WebApp();
        ServerBank1 serverBank1 = new ServerBank1(); // Можно закомментировать, app будет запускаться, но без серверного функционала
// // //        ServerBank2 serverBank2 = new ServerBank2(); // Пример другого сервера, можно подключить его
// // //        ServerDefault serverDefault = new ServerDefault(); // Пример сервера с дефолтным (никаким) функционалом

//        // Spring реализация
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
//        WebApp app = context.getBean("webapp", WebApp.class);
//        ServerBank1 serverBank1 = context.getBean("server1", ServerBank1.class);
// // //        ServerBank2 serverBank2 = context.getBean("server2", ServerBank2.class);
// // //        ServerDefault serverDefault = context.getBean("serverDefault", ServerDefault.class);

        app.runApp();

    }
}
