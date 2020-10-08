package ru.sbrf.payment.common;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private String phone = "";
    private String userName = "NoName";
    private String account = "XXXXX";
    private double balance = -1.0;

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
