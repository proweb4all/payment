package ru.sbrf.payment.app;

import lombok.*;

@ToString
@Getter

public enum AuthStatus {
    A0("Начальное состояние пользователя"),
    A1("Успешная авторизация пользователя"),
    A2("Неудачная авторизация: пользователь не найден"),
    A3("Неудачная авторизация: неверный пароль");
    private String descr = "";

    AuthStatus(String descr) { this.descr = descr; }

}
