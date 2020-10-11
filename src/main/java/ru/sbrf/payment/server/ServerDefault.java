package ru.sbrf.payment.server;

import java.util.Optional;

public class ServerDefault extends BaseServer{

    public ServerDefault() {
        serverLink = Optional.of(this);
    }

}
