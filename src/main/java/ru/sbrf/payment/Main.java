package ru.sbrf.payment;

import ru.sbrf.payment.app.App;
import ru.sbrf.payment.db.UsersDB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Инициалитизация БД:");
        UsersDB usersDB = new UsersDB();
        usersDB.init();
        System.out.println(usersDB.toString());
        App app = new App();
        System.out.println("Запустили приложение:\n" + app.toString());
        if (app.authUser("1123456789", "1123", usersDB)) {
            if (!app.payApp("1122334455", 50.0, usersDB)) {
                System.out.println("=== payApp - Платеж отклонен");
            }
        } else {
            System.out.println("--- Вы не авторизованы в приложении! Без этого отправка платежей невозможна. ---");
        }
    }
}
