package ru.sbrf.payment.db;

import java.util.HashMap;
import java.util.Map;
//import lombok.extern.slf4j.Slf4j;
import lombok.*;
@ToString
@Getter
//@Slf4j


public abstract class PaymentsDB implements IPaymentsDB{
    private final Map<String, Payment> paymentsDB = new HashMap<>();

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
