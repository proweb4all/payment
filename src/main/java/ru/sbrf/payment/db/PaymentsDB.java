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
        paymentsDB.put("0123456789_1122334455", new PaymentDB("0123456789_1122334455", new Date(), PaymentStatus.PS5, "0123456789", "1122334455", 100.0));
    }

    public boolean addPaymentToDB(PaymentDB paymentDB) {
        paymentsDB.put(paymentDB.getId(), paymentDB);
        //System.out.println("PaymentsDB: " + paymentsDB);
        return true;
    }
}
