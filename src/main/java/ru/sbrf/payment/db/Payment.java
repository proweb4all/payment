package ru.sbrf.payment.db;

import java.util.Date;

import lombok.*;
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
    private String id = "";
    private String payerPhone = "";
    private String account = "";
    private Date paymentDate = new Date();
    private PaymentStatus paymentStatus = PaymentStatus.PS0;
    private String payeePhone = "";
    private double amount = -1.0;

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
