package ru.sbrf.payment.db;

import java.util.Date;

public class PaymentsDBBank1 extends PaymentsDB{
    @Override
    public void init() {
        getPaymentsDB().put("9101111111_test-init-Sber", new Payment("9101111111_test-init-Sber", "9101111111", "XXXXX810X53001111111", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
        getPaymentsDB().put("9102222222_test-init-Sber", new Payment("9102222222_test-init-Sber", "9102222222", "XXXXX810X53002222222", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
        getPaymentsDB().put("9103333333_test-init-Sber", new Payment("9103333333_test-init-Sber", "9103333333", "XXXXX810X53003333333", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
    }

}
