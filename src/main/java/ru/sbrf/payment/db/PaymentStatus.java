package ru.sbrf.payment.db;

import lombok.*;
@ToString
@Getter

public enum PaymentStatus {
    PS0("Пустой шаблон"),
    PS1("1. Платеж создан"),
    PS2("2. Платеж проверен по БД"),
    PS3("3. Платеж подтвержден API сотового оператора"),
    PS4("4. Платеж проведен в БД клиентов"),
    PS5("5. Платеж сохранен в БД платежей"),
    PS6("6. Корректировка суммы остатка клиента в приложении"),
    PS11("--- Ошибка создания платежа"),
    PS12("--- Платеж отклонен после проверки по БД"),
    PS13("--- Платеж отклонен API сотового оператора"),
    PS14("--- Проблема с корректировкой суммы остатка в БД клиентов"),
    PS15("--- Проблема с сохранением платежа в БД платежей"),
    PS16("--- Проблема с корректировкой суммы остатка клиента в приложении");
    private String descr;

    PaymentStatus(String descr) { this.descr = descr; }

}