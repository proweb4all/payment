package ru.sbrf.payment;

class SomeException extends Exception{
    private String outText = "Не инициализировано (default)";
    SomeException() {}
    SomeException(String text) {
        outText = text;
    }

    @Override
    public String toString() {
        return "SomeException: " + outText;
    }
}
