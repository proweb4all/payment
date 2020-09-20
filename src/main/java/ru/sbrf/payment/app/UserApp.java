package ru.sbrf.payment.app;

import lombok.*;
import ru.sbrf.payment.common.User;

//@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserApp extends User {
    private String auth = "";

    public UserApp(String auth, String phone, String userName, double balance) {
        super(phone, userName, balance);
        this.auth = auth;
    }

    void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "UserApp(auth=" + this.getAuth() + ", " + super.toString() + ")";
    }
}

