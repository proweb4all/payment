package ru.sbrf.payment.db;

import java.util.Date;

public class PaymentsDBVTB extends PaymentsDB{
    @Override
    public void init() {
        paymentsDB.put("9101111111_test-init-VTB", new Payment("9101111111_test-init-VTB", "9101111111", "YYYYY810X53001111111", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
        paymentsDB.put("9102222222_test-init-VTB", new Payment("9102222222_test-init-VTB", "9102222222", "YYYYY810X53002222222", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
        paymentsDB.put("9103333333_test-init-VTB", new Payment("9103333333_test-init-VTB", "9103333333", "YYYYY810X53003333333", new Date(), PaymentStatus.PS5, "1122334455", 111.0));
    }

}
