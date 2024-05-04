package app.restman.api.services;

import app.restman.api.entities.Payment;
import app.restman.api.DTOs.PaymentCreateDTO;
import app.restman.api.DTOs.PaymentReturnDTO;
import app.restman.api.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

    public Payment createPayment(PaymentCreateDTO paymentCreateDTO) throws Exception {
        if (paymentCreateDTO.getAmount() < 0){
            var error = "Amount cannot be less than 0!";
            logger.log(Level.WARNING, error);
            throw new Exception(error);
        }

        //generate an id
        if (paymentCreateDTO.getPaymentId() == null
           || paymentCreateDTO.getPaymentId().isEmpty())
            paymentCreateDTO.setPaymentId(UUID.randomUUID().toString());

        //if order already has a payment for whatever reason, delete it
        try {
            var oldPayment = getByOrderId(paymentCreateDTO.getOrderId());
            paymentRepository.deleteById(oldPayment.getPaymentId());
        }
        catch (Exception e){
            ; //payment does not already exist for given order, do nothing
        }

        Payment payment = new Payment(
                paymentCreateDTO.getPaymentId(),
                paymentCreateDTO.getAmount(),
                paymentCreateDTO.isWithCash()
        );

        return paymentRepository.save(payment);
    }

    public PaymentReturnDTO getByOrderId(String orderId) throws Exception {
        Payment payment = paymentRepository.findByOrderId(orderId);
         if (payment == null) {
             logger.log(Level.SEVERE, "Number of products must match number of quantities!");
             throw new Exception("Given order has no payment!");
         }

        return new PaymentReturnDTO(payment);
    }
}