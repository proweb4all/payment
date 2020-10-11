package ru.sbrf.payment.server;

import ru.sbrf.payment.db.*;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import lombok.*;
@ToString
@Getter
@Slf4j

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
