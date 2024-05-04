package app.restman.api.DTOs;

import app.restman.api.entities.Payment;
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

    public PaymentReturnDTO(Payment payment){
        this.paymentId = payment.getPaymentId();
        this.time = payment.getTime();
        this.amount = payment.getAmount();
        this.tax = payment.getTax();
        this.net = payment.getNet();
        this.withCash = payment.isWithCash();
        this.orderId = payment.getOrder().getOrderId();
    }
}
