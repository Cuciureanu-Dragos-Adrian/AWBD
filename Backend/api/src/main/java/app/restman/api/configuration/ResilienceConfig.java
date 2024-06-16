package app.restman.api.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public Retry retry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3) // Maximum number of retry attempts
                .waitDuration(Duration.ofMillis(500)) // Delay between retries
                .build();

        return Retry.of("paymentServiceRetry", config);
    }

    @Bean
    public CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // Threshold for failure rate percentage
                .waitDurationInOpenState(Duration.ofSeconds(5)) // Time to wait before attempting to close the circuit
                .slidingWindowSize(10) // Number of calls in the sliding window
                .build();

        return CircuitBreaker.of("paymentServiceCircuitBreaker", config);
    }
}
