package ru.sbrf.payment.db;

import java.util.Date;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDB {
    private String id = "";
    private Date paymentDate;
    private String status = "";
    private String payerPhone = "";
    private String payeePhone = "";
    private double amount = -1.0;

    public void setStatus(String status) {
        this.status = status;
    }

}
