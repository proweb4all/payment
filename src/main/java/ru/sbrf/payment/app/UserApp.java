package ru.sbrf.payment.app;

import ru.sbrf.payment.common.User;

import lombok.*;
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserApp extends User {
    private StatusAuth authEnum = StatusAuth.A0;

    public UserApp(StatusAuth authEnum, String phone, String userName, String account, double balance) {
        super(phone, userName, account, balance);
        this.authEnum = authEnum;
    }

//    void setAuthEnum(StatusAuth authEnum) {
//        this.authEnum = authEnum;
//    }

    @Override
    public String toString() {
        return "UserApp(statusAuth=" + this.getAuthEnum() + ", " + super.toString() + ")";
    }
}

