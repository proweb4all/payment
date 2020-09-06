package ru.sbrf.sbol;

public class User implements UserValidation{
    //номер телефона, сумма, валюта, номер счета, номер клиента
    private String phoneNumber = "";
    private String clientNumber = "";
    private String accountNumber = "";
    private String currency = "RUB";
    private float amount;

    public User() {}
    public User(String phoneNumber, String clientNumber, String accountNumber, String currency, float amount) {
        this.phoneNumber = phoneNumber;
        this.clientNumber = clientNumber;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public String checkPhoneNumber(String phoneNumber) {
        try {
            if (phoneNumber.length() != 10) throw new SomeException("Длина телефонного номера не равна 10 символам");
        } catch (SomeException e) {
            phoneNumber = "";
            System.out.println( "Пepexвaчeнo исключение: \"" + e + "\". Возвращена пустая строка." ) ;
        } finally {
            return phoneNumber;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if (checkPhoneNumber(phoneNumber).length() > 0) { this.phoneNumber = phoneNumber; }
    }
    public String getPhoneNumber() { return phoneNumber; }

    public void setClientNumber(String clientNumber) { this.clientNumber = clientNumber; }
    public String getClientNumber() { return clientNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getAccountNumber() { return accountNumber; }

    public void setCurrency(String currency) { this.currency = currency; }
    public String getCurrency() { return currency; }

    public void setAmount(float amount) { this.amount = amount; }
    public float getAmount() { return amount; }


    @Override
    public String toString() {
        return phoneNumber + ", " + clientNumber + ", " + accountNumber + ", " + currency + ", " + amount;
    }
}
