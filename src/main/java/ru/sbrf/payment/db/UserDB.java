package ru.sbrf.payment.db;

import ru.sbrf.payment.common.User;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserDB extends User {
    private String password = "";
    public UserDB(String password, String phone, String userName, double balance) {
        super(phone, userName, balance);
        this.password = password;
    }
    @Override
    public String toString() {
        return "UserDB(password=" + this.getPassword() + ", " + super.toString() + ")";
    }
}
