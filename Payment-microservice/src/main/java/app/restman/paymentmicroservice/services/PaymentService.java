package app.restman.paymentmicroservice.services;

import app.restman.paymentmicroservice.model.PaymentCreateDTO;
import app.restman.paymentmicroservice.model.PaymentReturnDTO;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentReturnDTO getByOrderId(String orderId) {
        // Placeholder method, does nothing
        return null;
    }

    public PaymentReturnDTO createPayment(PaymentCreateDTO paymentCreateDTO) {
        // Placeholder method, does nothing
        PaymentReturnDTO result = new PaymentReturnDTO();
        result.setPaymentId("dummy");

        return result;
    }
}