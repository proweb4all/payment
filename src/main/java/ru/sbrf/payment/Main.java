package ru.sbrf.payment;

//import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import ru.sbrf.payment.app.App;
import ru.sbrf.payment.app.StatusAuth;
import ru.sbrf.payment.common.SomeException;
import ru.sbrf.payment.common.Validation;
import ru.sbrf.payment.common.ValidationValueFunc;

@Slf4j
public class Main {
    static <T> T validationValue(ValidationValueFunc<T> f, T s) throws SomeException {
        return f.func(s);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        App app = new App();
        app.init();
        log.info("=====================================================");
        log.info("☎☎☎ Приложение Payment запущено ");
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
            log.info("Выбран пункт меню: " + code);
            switch (code) {
                case 1:
                    System.out.print("Введите свой номер телефона (10 цифр): ");
                    String phone, pass;
                    try {
//                        phone = Validation.checkPhone(in.nextLine());
                        phone = validationValue(Validation::checkPhone, in.nextLine());
                    } catch (SomeException e) {
                        System.out.println(e);
                        log.info(String.valueOf(e));
                        break;
                    }
                    System.out.print("Введите пароль: ");
//                    // Вариант сокрытия символов пароля при вводе с консоли. Не работает из-под IDE.
//                    Console console = System.console();
//                    char passwordChars[] = console.readPassword();
//                    String pass = new String(passwordChars);
                    try {
//                        pass = Validation.checkPass(in.nextLine());
                        pass = validationValue(Validation::checkPass, in.nextLine());
                    } catch (SomeException e) {
                        System.out.println(e);
                        log.info(String.valueOf(e));
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
                            payeePhone = validationValue(Validation::checkPhone, in.nextLine());
                        } catch (SomeException e) {
                            System.out.println(e);
                            log.info(String.valueOf(e));
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
                                log.info(String.valueOf(e));
                            } catch (NumberFormatException e) {
                                System.out.println("Ошибка. Вы не ввели число. Введите сумму платежа в пределах Вашего остатка (0 - отмена): ");
                                log.info("Ошибка ввода суммы платежа: не введено число.");
                            }
                        } while (true);
                        if (amount != 0) {
                            app.payApp(payeePhone, amount, app.getUsersDB());
                        } else {
                            System.out.println("--- Вы отменили платеж.");
                            log.info("--- Пользователь отменил платеж.");
                        }
                    } else {
                        System.out.println("Введите правильное значение...");
                    }
                    break;
                case 0:
                    System.out.println("====================================");
                    System.out.println("Вы вышли из приложения. До свидания!");
                    System.out.println("====================================");
                    break;
                default:
                    System.out.print("Введите правильное значение...");
                    break;
            }
            System.out.println();
        } while (code != 0);
        in.close();
        log.info("☎☎☎ Приложение Payment закрыто");
        log.info("=====================================================");
    }
}
