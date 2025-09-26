package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.Symbols;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BinanceServiceTest {

    private BinanceService binanceService;
    private SpotRestApi spotRestApi;
    private BinanceConfig binanceConfig;

    @BeforeEach
    void setUp() {
        binanceConfig = mock(BinanceConfig.class);
        spotRestApi = mock(SpotRestApi.class);

        Mockito.when(binanceConfig.connectSpot()).thenReturn(spotRestApi);

        binanceService = new BinanceService(binanceConfig);
    }

    @Test
    void shouldCallMethodTickerPrice() {
        String symbol = "BNBUSDT";

        TickerPriceResponse1 innerResponse = new TickerPriceResponse1();
        innerResponse.setPrice("123.45");
        innerResponse.setSymbol(symbol);

        TickerPriceResponse responseWrapper = new TickerPriceResponse(innerResponse);

        ApiResponse<TickerPriceResponse> apiResponse = mock(ApiResponse.class);
        when(apiResponse.getData()).thenReturn(responseWrapper);
        when(spotRestApi.tickerPrice(eq("BNBUSDT"), any(Symbols.class))).thenReturn(apiResponse);

        BigDecimal result = binanceService.tickerPrice("BNBUSDT");

        assertEquals(new BigDecimal("123.45"), result);

        verify(spotRestApi, times(1)).tickerPrice(eq("BNBUSDT"), any(Symbols.class));
    }
    //TODO The test is not working
}