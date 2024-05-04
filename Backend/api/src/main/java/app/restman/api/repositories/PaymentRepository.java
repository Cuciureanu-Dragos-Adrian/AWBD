package app.restman.api.repositories;

import app.restman.api.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT new app.restman.api.entities.Payment(p) FROM Payment p WHERE p.order.orderId = :orderId")
    Payment findByOrderId(String orderId);
}
