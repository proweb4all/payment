package ru.sbrf.payment;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.BaseServer;
// import ru.sbrf.payment.server.ServerBank1;
// import ru.sbrf.payment.server.ServerBank2;

public class Main {
    public static void main(String[] args) {

//        // Обычная реализация
//        WebApp app = new WebApp();
//        BaseServer serverBank = new ServerBank1(); // Можно закомментировать, app будет запускаться, но без серверного функционала
// // //        ... new ServerBank2(); // Пример другого сервера, можно подключить его

//        // Spring реализация
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        WebApp app = context.getBean("webapp", WebApp.class);
        BaseServer serverBank = context.getBean("serverBank", BaseServer.class);

        app.runApp();

    }
}
