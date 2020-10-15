package ru.sbrf.payment.app;

public interface IApp {
    void runApp();
    boolean authUserApp(UserApp userApp);
    boolean payApp(String payeePhone, double amount);
}
