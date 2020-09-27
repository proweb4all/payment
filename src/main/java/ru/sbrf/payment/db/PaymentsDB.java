package ru.sbrf.payment.db;

import java.util.Date;
import java.util.HashMap;
import lombok.*;

@ToString
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor

public class PaymentsDB {
    HashMap<String, Payment> paymentsDB = new HashMap<>();

    public void init() {
        paymentsDB.put("9101111111_test-init", new Payment("9101111111_test-init", "9101111111", "XXXXX810X53001111111", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
        paymentsDB.put("9102222222_test-init", new Payment("9102222222_test-init", "9102222222", "XXXXX810X53002222222", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
        paymentsDB.put("9103333333_test-init", new Payment("9103333333_test-init", "9103333333", "XXXXX810X53003333333", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
    }

    public static String createPaymentID(String phone, Date date) {
        return phone + "_" + date.getTime();
    }

    public boolean addPaymentToDB(Payment payment) {
        paymentsDB.put(payment.getId(), payment);
//        System.out.println("БД платежей - PaymentsDB:");
//        for (HashMap.Entry<String, Payment> elem : paymentsDB.entrySet())
//            { System.out.println(elem.getKey() + ": " + elem.getValue()); }
        return true;
    }

    public boolean checkPaymentID(String id) {
        return (paymentsDB.get(id) == null);
    }
}
