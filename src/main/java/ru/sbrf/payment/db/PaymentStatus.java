package ru.sbrf.payment.db;

import lombok.*;
@ToString
@Getter

public enum PaymentStatus {
    PS0("Пустой шаблон"),
    PS1("1. Платеж создан"),
    PS2("2. Платеж проверен по БД"),
    PS3("3. Платеж отправлен в API сотового оператора"),
    PS4("4. Платеж подтвержден API сотового оператора"),
    PS5("5. Платеж проведен в БД"),
    PS11("--- Платеж отклонен после проверки по БД"),
    PS12("--- Платеж отклонен API сотового оператора");
    private String descr = "";

    PaymentStatus(String descr) { this.descr = descr; }

}
