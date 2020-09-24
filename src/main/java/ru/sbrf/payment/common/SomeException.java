package ru.sbrf.payment.common;

import lombok.*;
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class SomeException extends Exception{
    private String outText = "Не инициализировано (default)";

    @Override
    public String toString() {
        return "Ошибка. " + outText;
    }
}