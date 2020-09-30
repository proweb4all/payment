package ru.sbrf.payment.common;

public interface ValidationValueFunc<T> {
    T func(T str) throws SomeException;
}
