package ru.sbrf.payment;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.BaseServer;
// import ru.sbrf.payment.server.ServerBank1;
// import ru.sbrf.payment.server.ServerBank2;

@ComponentScan(basePackages = "ru.sbrf.payment")
public class Main {
    public static void main(String[] args) {

//        // ========== Обычная реализация
//        WebApp app = new WebApp();
//        BaseServer serverBank = new ServerBank1(); // Можно закомментировать, app будет запускаться, но без серверного функционала
// // //        ... new ServerBank2(); // Пример другого сервера, можно подключить его

//        // ========== Spring реализация

//        // XML-конфигурация
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

//        // Annotation-конфигурация
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        WebApp app = context.getBean("webApp", WebApp.class);
        BaseServer serverBank1 = context.getBean("serverBank1", BaseServer.class);
//        BaseServer serverBank2 = context.getBean("serverBank2", BaseServer.class);

        app.runApp();

    }
}
