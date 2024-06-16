package app.restman.paymentmicroservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("payments")
@Getter
@Setter
public class PropertiesConfig {
    private boolean dummy;
}