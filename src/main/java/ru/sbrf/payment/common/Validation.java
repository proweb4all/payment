package ru.sbrf.payment.common;

public class Validation {

    public static String checkPhone(String phone) throws SomeException{
        String realPhone = phone.replaceAll("\\D+","");
        if (realPhone.length() < 10) {throw new SomeException("Длина телефонного номера менее 10 цифр");}
        return realPhone.substring(0, 10);
    }
    public static String checkPass(String pass) throws SomeException{
        if (pass.length() < 6) {throw new SomeException("Длина пароля менее 6 символов");}
        return pass;
    }
    public static double checkAmount(String amountStr, double balance) throws SomeException{
        double amount = Double.parseDouble(amountStr.replaceAll(" ", "").replaceAll(",", "."));
        if (amount < 0 || amount > balance) {
            throw new SomeException("Введите сумму больше нуля в пределах Вашего остатка (0 - отмена): ");}
        return amount;
    }

}
