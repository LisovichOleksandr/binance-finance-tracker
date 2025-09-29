package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse;
import com.binance.connector.client.spot.rest.model.TickerPriceResponse1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BinanceServiceTest {

    @Mock private BinanceConfig binanceConfig;
    @Mock private SpotRestApi spotRestApi;
    @InjectMocks private BinanceService binanceService;

    @Test
    void shouldGetTickerPriceForSymbol() {
        String symbol = "BNBUSDT";

        TickerPriceResponse1 innerResponse = new TickerPriceResponse1();
        innerResponse.setPrice("123.45");
        innerResponse.setSymbol(symbol);

        TickerPriceResponse responseWrapper = new TickerPriceResponse(innerResponse);

        ApiResponse<TickerPriceResponse> apiResponse = mock(ApiResponse.class);
        when(apiResponse.getData()).thenReturn(responseWrapper);

        when(binanceConfig.connectSpot()).thenReturn(spotRestApi);
        when(spotRestApi.tickerPrice(eq("BNBUSDT"), isNull())).thenReturn(apiResponse);


        BigDecimal result = binanceService.tickerPrice("BNBUSDT");

        assertEquals(new BigDecimal("123.45"), result);
    }

    @Test
    void shouldCalledTickerPrice() {
        // Given
        String symbol = "BNBUSDT";

        TickerPriceResponse1 innerResponse = new TickerPriceResponse1();
        innerResponse.setPrice("123.45");
        innerResponse.setSymbol(symbol);

        TickerPriceResponse responseWrapper = new TickerPriceResponse(innerResponse);

        ApiResponse<TickerPriceResponse> apiResponse = mock(ApiResponse.class);
        when(apiResponse.getData()).thenReturn(responseWrapper);

        when(binanceConfig.connectSpot()).thenReturn(spotRestApi);
        when(spotRestApi.tickerPrice(eq("BNBUSDT"), isNull())).thenReturn(apiResponse);

        // When
        BigDecimal result = binanceService.tickerPrice("BNBUSDT");

        // Then
        verify(spotRestApi, times(1)).tickerPrice(eq("BNBUSDT"), isNull());
    }
}