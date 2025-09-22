package by.lisovich.binance_finance_tracker.binance;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class BinanceConfig {

    @Value("${binance.api.key}")
    private String apiKey;

    @Value("${binance.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        System.out.println("API Key = " + apiKey);
        System.out.println("Secret Key = " + secretKey);
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }
}
