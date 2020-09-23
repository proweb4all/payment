package ru.sbrf.payment;

import ru.sbrf.payment.app.App;
import ru.sbrf.payment.app.StatusAuth;
import ru.sbrf.payment.db.UsersDB;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        UsersDB usersDB = new UsersDB();
        usersDB.init();
        App app = new App();
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
                System.out.println("==============================================\nВыберите и введите номер дальнейшего действия: ");
                System.out.println("2 - оплата мобильного телефона");
            } else {
                System.out.println("!-- Вы не авторизованы в приложении");
                System.out.println("==============================================\nВыберите и введите номер дальнейшего действия: ");
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
                    System.out.print("Введите свой номер телефона \"1123456789\": ");
                    String phone = in.nextLine();
                    System.out.print("Введите пароль: ");
//                    // Вариант сокрытия символов пароля при вводе с консоли. Не работает из-под IDE.
//                    Console console = System.console();
//                    char passwordChars[] = console.readPassword();
//                    String pass = new String(passwordChars);
                    String pass = in.nextLine();
                    System.out.println("phone:" + phone +", pass: " + pass);
                    app.authUser(phone, pass, usersDB);
                    break;
                case 2:
                    System.out.print("Введите номер телефона получателя платежа (10 цыфр) \"1122334455\": ");
                    String payeePhone = in.nextLine();
                    System.out.print("Введите сумму платежа в пределах Вашего остатка: ");
                    double amount = 0.0;
                    do {
                        if (in.hasNextDouble()) {
                            amount = in.nextDouble();
                            break;
                        } else {
                            System.out.print("Вы не ввели число. Попробуйте еще раз: ");
                            in.nextLine();
                        }
                    } while (true);
                    app.payApp(payeePhone, amount, usersDB);
                    break;
                case 0:
                    System.out.println("====================================\nВы вышли из приложения. До свидания!\n====================================");
                    break;
                default:
                    //System.out.print("Введите правильное значение...");
                    break;
            }
            System.out.println("\n");
        } while (code != 0);
        in.close();
    }
}
