package ru.sbrf.payment.common;

import lombok.*;
@ToString
@Getter
public class Settings {
    private String hostAddress = "localhost";
    private String ipAddress = "192.168.0.1";
    private String port = "5000";
    private String protocol = "HTTPS";
}
