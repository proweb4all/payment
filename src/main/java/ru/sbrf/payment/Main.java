package ru.sbrf.payment;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.BaseServer;

public class Main {
    public static void main(String[] args) {

//        // Java-code конфигурация
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        WebApp app = context.getBean("webApp", WebApp.class);
        BaseServer serverBank1 = context.getBean("serverBank1", BaseServer.class);
//        BaseServer serverBank2 = context.getBean("serverBank2", BaseServer.class);

        app.runApp();

    }
}
