package ru.sbrf.payment.common;

import lombok.*;
@ToString
@Getter
public class Settings {
    private final String hostAddress = "localhost";
    private final String ipAddress = "192.168.0.1";
    private final String port = "5000";
    private final String protocol = "HTTPS";
}
