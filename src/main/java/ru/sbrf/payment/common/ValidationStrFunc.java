package ru.sbrf.payment.common;

public interface ValidationStrFunc<T> {
    String func(T str) throws SomeException;
}
