package ru.sbrf.payment.server;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sbrf.payment.db.*;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import lombok.*;
@ToString
@Getter
@Slf4j
@Component
@Scope("prototype")
public class ServerBank2 extends BaseServer{

    public ServerBank2() {
        serverLink = Optional.of(this);
        setNameServer("ServerBank2");
        setUsersDBBank(new UsersDBBank2());
        getUsersDBBank().init();
        setPaymentsDBBank(new PaymentsDBBank2());
        getPaymentsDBBank().init();
    }
}
