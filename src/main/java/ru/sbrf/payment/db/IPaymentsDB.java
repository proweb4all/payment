package ru.sbrf.payment.db;

public interface IPaymentsDB {
    void init();
    boolean addPaymentToDB(Payment payment);
    boolean checkPaymentID(String id);
}
