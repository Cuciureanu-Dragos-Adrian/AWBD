package app.restman.api.controllers;

import app.restman.api.DTOs.PaymentCreateDTO;
import app.restman.api.DTOs.PaymentReturnDTO;
import app.restman.api.entities.Payment;
import app.restman.api.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<PaymentReturnDTO> getPaymentByOrderId(@PathVariable String orderId) {
        try {
            PaymentReturnDTO paymentReturnDTO = paymentService.getByOrderId(orderId);
            return ResponseEntity.ok(paymentReturnDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) {
        try {
            Payment payment = paymentService.createPayment(paymentCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully with ID: " + payment.getPaymentId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
