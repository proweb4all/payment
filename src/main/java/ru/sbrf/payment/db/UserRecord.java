package ru.sbrf.payment.db;

import ru.sbrf.payment.common.User;
import lombok.*;
@Getter

public class UserRecord extends User {
    private String password = "";
    public UserRecord(String password, String phone, String userName, String account, double balance) {
        super(phone, userName, account, balance);
        this.password = password;
    }
    @Override
    public String toString() {
        return "UserDB(password=" + this.getPassword() + ", " + super.toString() + ")";
    }
}
