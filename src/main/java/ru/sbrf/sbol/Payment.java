package ru.sbrf.sbol;

import java.util.Date;

public class Payment {
    //сумма платежа, счет платежа, время платежа, номер платежа
    private String id = "";
    private String account = "";
    private Date dateTime = new Date();
    private String currency = "RUB";
    private float amount;

    public Payment() {}
    public Payment(String id, String account, Date dateTime, String currency, float amount) {
        this.id = id;
        this.account = account;
        this.dateTime = dateTime;
        this.currency = currency;
        this.amount = amount;
    }

    public void setId(String id) { this.id = id; }
    public String getId() { return id; }

    public void setAccount(String account) { this.account = account; }
    public String getAccount() { return account; }

    public void setDateTime(Date dateTime) { this.dateTime = dateTime; }
    public Date getDateTime() { return dateTime; }

    public void setCurrency(String currency) { this.currency = currency; }
    public String getCurrency() { return currency; }

    public void setAmount(float amount) { this.amount = amount; }
    public float getAmount() { return amount; }

    @Override
    public String toString() {
        return id + ", " + account + ", " + dateTime + ", " + currency + ", " + amount;
    }

}
