package ru.sbrf.payment.server;

import lombok.*;

import java.util.Date;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String id = "";
    private Date paymentDate;
    private String status = "";
    private String payerPhone = "";
    private String payeePhone = "";
    private double amount = -1.0;

    void setStatus(String status) {
        this.status = status;
    }

}
