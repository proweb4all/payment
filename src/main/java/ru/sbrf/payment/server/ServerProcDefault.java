package ru.sbrf.payment.server;

import java.util.Optional;

public class ServerProcDefault extends BaseServer{

    public ServerProcDefault() {
        serverLink = Optional.of(this);
    }

}
