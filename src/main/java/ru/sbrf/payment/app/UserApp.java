package ru.sbrf.payment.app;

import ru.sbrf.payment.common.User;

import lombok.*;
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserApp extends User {
    private AuthStatus authStatus = AuthStatus.A0;

    public UserApp(AuthStatus authStatus, String phone, String userName, String account, double balance) {
        super(phone, userName, account, balance);
        this.authStatus = authStatus;
    }

    @Override
    public String toString() {
        return "UserApp(statusAuth=" + this.getAuthStatus() + ", " + super.toString() + ")";
    }
}

