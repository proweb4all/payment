package ru.sbrf.payment;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import ru.sbrf.payment.app.App;
import ru.sbrf.payment.app.StatusAuth;
import ru.sbrf.payment.common.SomeException;
import ru.sbrf.payment.common.Validation;
import ru.sbrf.payment.common.ValidationStrFunc;

public class Main {
    static String validationStr(ValidationStrFunc f, String s) throws SomeException {
        return f.func(s);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        App app = new App();
        app.init();
        int code = 11;
        boolean auth = false;
        String inStr;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("=====================================================");
            System.out.println("☎☎☎ ПРИЛОЖЕНИЕ ДЛЯ ОПЛАТЫ МОБИЛЬНОГО ТЕЛЕФОНА ☎☎☎");
            System.out.println("=====================================================");
            auth = (app.getUser().getAuthEnum() == StatusAuth.A1);
            if (auth) {
                System.out.println("+++ Вы авторизованы как " + app.getUser().getUserName() + ", т." + app.getUser().getPhone() + ", остаток средств " + app.getUser().getBalance() + "руб.");
                System.out.println("==============================================\nВведите номер дальнейшего действия: ");
                System.out.println("1 - авторизация в приложении");
                System.out.println("2 - оплата мобильного телефона");
            } else {
                System.out.println("!-- Вы не авторизованы в приложении");
                System.out.println("==============================================\nВведите номер дальнейшего действия: ");
                System.out.println("1 - авторизация в приложении");
            }
            System.out.print("0 - выход\n> ");
            do {
                inStr = in.nextLine();
                if (inStr.length() > 0) { break; }
            } while (true);
            code = Character.getNumericValue(inStr.charAt(0));
            switch (code) {
                case 1:
                    System.out.print("Введите свой номер телефона (10 цифр): ");
                    String phone, pass;
                    try {
//                        phone = Validation.checkPhone(in.nextLine());
                        phone = validationStr(Validation::checkPhone, in.nextLine());
                    } catch (SomeException e) {
                        System.out.println(e);
                        break;
                    }
                    System.out.print("Введите пароль: ");
//                    // Вариант сокрытия символов пароля при вводе с консоли. Не работает из-под IDE.
//                    Console console = System.console();
//                    char passwordChars[] = console.readPassword();
//                    String pass = new String(passwordChars);
                    try {
//                        pass = Validation.checkPass(in.nextLine());
                        pass = validationStr(Validation::checkPass, in.nextLine());
                    } catch (SomeException e) {
                        System.out.println(e);
                        break;
                    }
                    app.authUser(phone, pass, app.getUsersDB());
                    break;
                case 2:
                    if (auth) {
                        System.out.print("Введите номер телефона получателя платежа (10 цифр): ");
                        String payeePhone;
                        try {
//                            payeePhone = Validation.checkPhone(in.nextLine());
                            payeePhone = validationStr(Validation::checkPhone, in.nextLine());
                        } catch (SomeException e) {
                            System.out.println(e);
                            break;
                        }
                        System.out.print("Введите сумму платежа в пределах Вашего остатка (0 - отмена): ");
                        double amount = 0.0;
                        do {
                            try {
                                amount = Validation.checkAmount(in.nextLine(), app.getUser().getBalance());
                                break;
                            } catch (SomeException e) {
                                System.out.println(e);
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка. Вы не ввели число. Введите сумму платежа в пределах Вашего остатка (0 - отмена): ");
                            }
                        } while (true);
                        if (amount != 0) {
                            app.payApp(payeePhone, amount, app.getUsersDB());
                        } else {
                            System.out.println("--- Вы отменили платеж.");
                        }
                    } else {
                        System.out.println("Введите правильное значение...");
                    }
                    break;
                case 0:
                    System.out.println("====================================\nВы вышли из приложения. До свидания!\n====================================");
                    break;
                default:
                    System.out.print("Введите правильное значение...");
                    break;
            }
            System.out.println();
        } while (code != 0);
        in.close();
    }
}
