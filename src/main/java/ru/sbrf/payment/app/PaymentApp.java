package ru.sbrf.payment.app;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApp {
    private String id = "";
    private String status = "";
    private String payeePhone = "";
    private double amount = -1.0;
}
