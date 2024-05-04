package app.restman.api.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentCreateDTO {
    private String paymentId;
    private OffsetDateTime time;
    private double amount;
    private boolean withCash;
    private String orderId; // Use Long instead of Order for potential decoupling
}