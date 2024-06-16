package app.restman.paymentmicroservice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentReturnDTO {
    private String paymentId;
    private OffsetDateTime time;
    private double amount;
    private double tax;
    private double net;
    private boolean withCash;
    private String orderId;

    public PaymentReturnDTO() { }
}
