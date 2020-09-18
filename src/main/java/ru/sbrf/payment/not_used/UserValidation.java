package ru.sbrf.payment.not_used;

public interface UserValidation{
    default String checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 0) {
            System.out.println("Поле phoneNumber пустое.");
        }
        return phoneNumber;
    }
    default String checkClientNumber(String clientNumber) {
        if (clientNumber.length() == 0) {
            System.out.println("Поле clientNumber пустое.");
        }
        return clientNumber;
    }
    default String checkAccountNumber(String accountNumber) {
        if (accountNumber.length() == 0) {
            System.out.println("Поле accountNumber пустое.");
        }
        return accountNumber;
    }
    default String checkCurrency(String currency) {
        if (currency.length() == 0) {
            System.out.println("Поле currency пустое.");
        }
        return currency;
    }
    default double checkAmount(double amount) {
        if (amount < 0) {
            System.out.println("Поле amount < 0. Возращен 0.");
        }
        return 0.0;
    }
}
