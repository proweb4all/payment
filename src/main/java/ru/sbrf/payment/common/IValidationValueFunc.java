package ru.sbrf.payment.common;

public interface IValidationValueFunc<T> {
    T func(T str) throws SomeException;
}
