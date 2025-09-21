package by.lisovich.binance_finance_tracker.binance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BinanceService {
    private final BinanceConfig binanceConfig;

    public void connect() {
        System.out.println("apiKey: " + binanceConfig.getApiKey());
    }

}
