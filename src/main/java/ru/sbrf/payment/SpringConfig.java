package ru.sbrf.payment;

import org.springframework.context.annotation.*;
import ru.sbrf.payment.app.WebApp;
import ru.sbrf.payment.server.BaseServer;
import ru.sbrf.payment.server.ServerBank1;
import ru.sbrf.payment.server.ServerBank2;

@Configuration
//@ComponentScan("ru.sbrf.payment")
//@PropertySource("classpath:paymentsApp.properties")
public class SpringConfig {
    // Для примера создаем все бины вручную
    @Bean
    public WebApp webApp() { return new WebApp(); }
    @Bean
    @Scope("prototype")
    public BaseServer serverBank1() { return new ServerBank1(); }
    @Bean
    @Scope("prototype")
    public BaseServer serverBank2() { return new ServerBank2(); }
}
