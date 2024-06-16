package app.restman.api.services;

import app.restman.api.DTOs.PaymentCreateDTO;
import app.restman.api.DTOs.PaymentReturnDTO;
import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {

    @Autowired
    private WebClient webClient;
    @Autowired
    private Retry retry;
    @Autowired
    private CircuitBreaker circuitBreaker;
    @Autowired
    private EurekaClient discoveryClient;
    @Autowired
    private PaymentServiceProxy paymentServiceProxy;

    public PaymentService() { }

    public PaymentReturnDTO getPaymentByOrderId(String orderId) {
        return paymentServiceProxy.getPaymentByOrderId(orderId);
    }

    public PaymentReturnDTO createPayment(PaymentCreateDTO paymentCreateDTO) {
        String url = discoveryClient.getNextServerFromEureka("PAYMENT", false).getHomePageUrl();

        return webClient.post()
                .uri(url + "/payments")
                .bodyValue(paymentCreateDTO)
                .retrieve()
                .bodyToMono(PaymentReturnDTO.class)
                .transformDeferred(RetryOperator.of(retry))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .block();
    }


}