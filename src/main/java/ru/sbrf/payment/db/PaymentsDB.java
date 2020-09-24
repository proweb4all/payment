package ru.sbrf.payment.db;

import java.util.Date;
import java.util.HashMap;
import lombok.*;

@ToString
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor

public class PaymentsDB {
    HashMap<String, PaymentDB> paymentsDB = new HashMap<>();

    public void init() {
        paymentsDB.put("0123456789_test-init", new PaymentDB("0123456789_test-init", new Date(), PaymentStatus.PS5, "0123456789", "1122334455", 111.0));
        paymentsDB.put("1123456789_test-init", new PaymentDB("1123456789_test-init", new Date(), PaymentStatus.PS5, "0123456789", "1122334455", 111.0));
        paymentsDB.put("2123456789_test-init", new PaymentDB("2123456789_test-init", new Date(), PaymentStatus.PS5, "0123456789", "1122334455", 111.0));
    }

    public boolean addPaymentToDB(PaymentDB paymentDB) {
        paymentsDB.put(paymentDB.getId(), paymentDB);
//        System.out.println("БД платежей - PaymentsDB:");
//        for (HashMap.Entry<String, PaymentDB> elem : paymentsDB.entrySet())
//            { System.out.println(elem.getKey() + ": " + elem.getValue()); }
        return true;
    }

}
