package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.model.Symbols;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BinanceService {

    private final BinanceConfig binanceConfig;


    public BigDecimal tickerPrice(String symbol) {
        Symbols symbols = null;
        ApiResponse<TickerPriceResponse> apiResponse = binanceConfig.connectSpot().tickerPrice(symbol, symbols);
        String price = apiResponse.getData().getTickerPriceResponse1().getPrice();
        return new BigDecimal(Objects.requireNonNull(price));
    }
}
