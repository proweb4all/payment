package ru.sbrf.payment.db;

import java.util.Date;
import java.util.HashMap;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j

public class PaymentsDB implements IPaymentsDB{
    HashMap<String, Payment> paymentsDB = new HashMap<>();

    @Override
    public void init() {}

    @Override
    public boolean addPaymentToDB(Payment payment) {
        paymentsDB.put(payment.getId(), payment);
//        log.info("БД платежей - PaymentsDB:");
//        for (HashMap.Entry<String, Payment> elem : paymentsDB.entrySet())
//            { log.info(elem.getKey() + ": " + elem.getValue()); }
        return true;
    }

    @Override
    public boolean checkPaymentID(String id) {
        return (paymentsDB.get(id) == null);
    }
}
