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
public class ServerBank1 extends BaseServer{

    public ServerBank1() {
        serverLink = Optional.of(this);
        setNameServer("ServerBank1");
        setUsersDBBank(new UsersDBBank1());
        getUsersDBBank().init();
        setPaymentsDBBank(new PaymentsDBBank1());
        getPaymentsDBBank().init();
    }

}
