package app.restman.paymentmicroservice.controller;

import app.restman.paymentmicroservice.config.PropertiesConfig;
import app.restman.paymentmicroservice.model.PaymentCreateDTO;
import app.restman.paymentmicroservice.model.PaymentReturnDTO;
import app.restman.paymentmicroservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RequestMapping("/payments")
@RestController
public class PaymentController {

    @Autowired
    private PropertiesConfig configuration;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<PaymentReturnDTO> getPaymentByOrderId(@PathVariable String orderId) {
        try {
            if (!configuration.isDummy())
            {
                PaymentReturnDTO payment = paymentService.getByOrderId(orderId);
                if (payment != null) {
                    return ResponseEntity.ok(payment);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
            else
            {
                //return a dummy payment
                PaymentReturnDTO payment = new PaymentReturnDTO();
                payment.setPaymentId("dummy-id");
                payment.setTime(OffsetDateTime.now());
                payment.setAmount(0.0);
                payment.setTax(0.0);
                payment.setNet(0.0);
                payment.setWithCash(false);
                payment.setOrderId(orderId);

                return ResponseEntity.ok(payment);

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) {
        try {
            PaymentReturnDTO payment = paymentService.createPayment(paymentCreateDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(payment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
