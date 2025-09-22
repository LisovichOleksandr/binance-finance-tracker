package by.lisovich.binance_finance_tracker.binance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class BinanceConfigTest {

    @Autowired
    private BinanceConfig binanceConfig;

    @Test
    void apiKeysShouldBeLoadedFromEnvironment() {
        Assertions.assertThat(binanceConfig.getApiKey())
                .as("API key mast be loaded")
                .isNotBlank();
    }

}