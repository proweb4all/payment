package ru.sbrf.payment.app;

public interface App {
    void runApp();
    boolean authUserApp(UserApp userApp);
    boolean payApp(String payeePhone, double amount);
}
