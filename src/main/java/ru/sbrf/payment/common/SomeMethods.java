package ru.sbrf.payment.common;

import java.util.Scanner;

public class SomeMethods {
    public static String getUserStr() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
}
