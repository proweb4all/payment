package ru.sbrf.payment.app;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserApp {
    private String phone = "";
    private String auth = "";
    private String userName = "";
    private double balance = -1.0;

    public UserApp(String phone) {
        this.phone = phone;
    }

    void setAuth(String auth) {
        this.auth = auth;
    }
    void setUserName(String userName) {
        this.userName = userName;
    }
    void setBalance(double balance) {
        this.balance = balance;
    }

}

