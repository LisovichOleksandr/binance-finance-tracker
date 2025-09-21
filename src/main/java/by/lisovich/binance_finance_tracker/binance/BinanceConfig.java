package by.lisovich.binance_finance_tracker.binance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConfig {

    @Value("${binance.api.key}")
    private String apiKey;

    @Value("${binance.secret.key}")
    private String secretKey;

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
