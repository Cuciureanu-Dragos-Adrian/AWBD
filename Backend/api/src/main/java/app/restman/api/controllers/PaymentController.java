package app.restman.api.controllers;

import app.restman.api.DTOs.PaymentCreateDTO;
import app.restman.api.DTOs.PaymentReturnDTO;
import com.netflix.discovery.EurekaClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final WebClient webClient;

    @Value("${payment.service.baseurl}")
    private String paymentServiceBaseUrl;

    @Autowired
    private Retry retry; // Inject the Retry bean

    @Autowired
    private CircuitBreaker circuitBreaker; // Inject the CircuitBreaker bean

    @Autowired
    private EurekaClient discoveryClient;

    public PaymentController(WebClient webClient) {
        this.webClient = webClient;
    }

    @Operation(summary = "Get payment by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved payment",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentReturnDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)})
    @GetMapping("/getByOrderId/{orderId}")
    public ResponseEntity<PaymentReturnDTO> getPaymentByOrderId(@PathVariable String orderId) {
        try {
            String url = discoveryClient.getNextServerFromEureka("PAYMENT", false).getHomePageUrl();

            PaymentReturnDTO paymentReturnDTO = webClient.get()
                    .uri(url + "/payments/getByOrderId/{orderId}", orderId)
                    .retrieve()
                    .bodyToMono(PaymentReturnDTO.class)
                    .transformDeferred(RetryOperator.of(retry))
                    .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                    .block();

            return ResponseEntity.ok(paymentReturnDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) {
        try {
            String url = discoveryClient.getNextServerFromEureka("PAYMENT", false).getHomePageUrl();

            PaymentReturnDTO payment = webClient.post()
                    .uri(url + "/payments")
                    .bodyValue(paymentCreateDTO)
                    .retrieve()
                    .bodyToMono(PaymentReturnDTO.class)
                    .transformDeferred(RetryOperator.of(retry))
                    .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                    .block();

            return ResponseEntity.status(HttpStatus.CREATED).body("Payment created successfully with ID: " + payment.getPaymentId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}