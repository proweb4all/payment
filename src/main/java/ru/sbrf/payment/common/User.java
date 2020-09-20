package ru.sbrf.payment.common;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private String phone = "";
    private String userName = "";
    private double balance = -1.0;

    public void setPhone(String phone) { this.phone = phone; }
    public
    void setUserName(String userName) { this.userName = userName; }
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
