package ru.sbrf.payment;

import ru.sbrf.payment.app.App;
import ru.sbrf.payment.app.UserApp;
import ru.sbrf.payment.app.PaymentApp;

public class Main {
    public static void main(String[] args) {
//        System.out.println("--- User1 ---");
//        User user1 = new User();
//        user1.setPhoneNumber("1234567890");
//        user1.setPhoneNumber("12345");
//        System.out.println(user1.toString());
//        System.out.println("--- User2 ---");
//        User user2 = new User("1234567", "123456", "40817810X53051234567", "RUB", 1000.0);
//        System.out.println(user2.toString());
//        System.out.println("--- AppSbol ---");
//        App app = new App();
//        System.out.println(app);
//        System.out.println("--- Payment ---");
//        Payment payment = new Payment();
//        System.out.println(payment);
        System.out.println("--- UserApp ---");
        UserApp user = new UserApp("0123456789");
        System.out.println(user.toString());
//        System.out.println(user.getBalance());
        System.out.println("--- App ---");
        App app = new App(user);
        System.out.println(app.toString());
        System.out.println(app.authUser("password"));
        System.out.println(app.toString());
        System.out.println(app.payApp());

//        System.out.println("--- PaymentApp ---");
//        PaymentApp payment = new PaymentApp("1", "0", "9106431234", 10.0);
//        System.out.println(payment.toString());
//        System.out.println(payment.getPayeePhone());
    }
}
