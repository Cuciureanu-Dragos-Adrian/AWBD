package app.restman.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
public class Payment {

    @Id
    private String paymentId;
    private OffsetDateTime time;
    private double amount;
    private double tax;
    private double net;
    private boolean withCash;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    public Payment(){
    }

    public Payment(String paymentId, double amount, boolean withCash){
        this.paymentId = paymentId;
        this.time = OffsetDateTime.now();
        this.withCash = withCash;

        this.amount = amount;
        this.tax = 0.09 * amount;   //9% VAT
        this.net = this.amount - this.tax;
    }
}
