package app.restman.api.services;

import app.restman.api.DTOs.PaymentCreateDTO;
import app.restman.api.DTOs.PaymentReturnDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT")
public interface PaymentServiceProxy {

    @GetMapping("/payments/getByOrderId/{orderId}")
    PaymentReturnDTO getPaymentByOrderId(@PathVariable("orderId") String orderId);

    @PostMapping("/payments")
    PaymentReturnDTO createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO);
}
