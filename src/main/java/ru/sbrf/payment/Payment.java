package ru.sbrf.payment;

import java.util.Date;

@lombok.Getter
public class Payment {
    //сумма платежа, счет платежа, время платежа, номер платежа
    private String id = "";
    private String account = "";
    private Date dateTime = new Date();
    private String currency = "RUB";
    private double amount;

    public Payment() {}
    public Payment(String id, String account, Date dateTime, String currency, double amount) {
        this.id = id;
        this.account = account;
        this.dateTime = dateTime;
        this.currency = currency;
        this.amount = amount;
    }

    public void setId(String id) { this.id = id; }

    public void setAccount(String account) { this.account = account; }

    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }

    public void setCurrency(String currency) { this.currency = currency; }

    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return id + ", " + account + ", " + dateTime + ", " + currency + ", " + amount;
    }

}
