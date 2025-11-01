package by.lisovich.binance_finance_tracker.slice.controller;

import by.lisovich.binance_finance_tracker.binance.dto.*;
import by.lisovich.binance_finance_tracker.controller.PriceController;
import by.lisovich.binance_finance_tracker.security.filter.JwtAuthenticationFilter;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import com.binance.connector.client.spot.rest.model.Interval;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = PriceController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        ))
@AutoConfigureMockMvc(addFilters = false)
public class PriceControllerGetKlinesTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    SymbolService symbolService;
    @MockitoBean
    BinanceService binanceService;
    @Autowired
    ObjectMapper objectMapper;

    String symbol = "BNBUSDT";
    private List<KlinesItemDto> klinesItemDefDtos = new ArrayList<>();
    private List<KlinesItemDto> klinesItem5mDtos = new ArrayList<>();

    @BeforeEach
    public void init() {
        KlinesItemDto klines1m_1 = new KlinesItemDto(
                1609459200000L, "28923.63", "28950.00", "28923.63", "28930.24", "12.345",
                1609459260000L, "356789.12", 154, "6.789", "195678.44", "0"
        );

        KlinesItemDto klines1m_2 = new KlinesItemDto(
                1609459260000L, "28930.24", "28960.55", "28910.10", "28950.12", "10.982",
                1609459320000L, "318452.77", 143, "4.551", "132551.11", "0"
        );
        klinesItemDefDtos.add(klines1m_1);
        klinesItemDefDtos.add(klines1m_2);

        KlinesItemDto klines5m_1 = new KlinesItemDto(
                1609459200000L, "28923.63", "28980.00", "28900.00", "28950.24", "25.678",
                1609459500000L, "745123.55", 421, "12.456", "389551.22", "0"
        );

        KlinesItemDto klines5m_2 = new KlinesItemDto(
                1609459500000L, "28950.24", "29010.55", "28920.10", "28990.12", "30.982",
                1609459800000L, "912452.77", 502, "15.551", "455112.11", "0"
        );
        klinesItem5mDtos.add(klines5m_1);
        klinesItem5mDtos.add(klines5m_2);
    }


    @Test
    public void givenValidRequest_whenGetKlines_thenReturnListKlinesItemDtoAnd200() throws Exception {
        //given
        when(binanceService.getKlines(symbol, any(), any(), any(), any())).thenReturn(klinesItemDefDtos);

        // when
        ResultActions perform = mockMvc.perform(get("/api/prices/" + symbol + "/klines"));

        // then
        perform.andExpect(status().isOk());
        perform.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        perform.andExpect(jsonPath("$").isArray());
        perform.andExpect(jsonPath("$.length").value(2));
        perform.andExpect(jsonPath("$[0].openTime").value("1609459200000"));

        List<KlinesItemDto> klines = objectMapper
                .readValue(perform.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});
        for (KlinesItemDto kline : klines) {
            long interval = kline.closeTime() - kline.openTime();
            assertThat(interval)
                    .as("Checking that the candle is a minute candle")
                    .isBetween(59_000L, 61_000L);
        }

        verify(binanceService, times(1)).getKlines(any(), any(), any(), any(), any());
    }

    /**
     * Supported kline intervals (case-sensitive):
     *
     * Interval	 value
     * seconds	 1s
     * minutes	 1m, 3m, 5m, 15m, 30m
     * hours	 1h, 2h, 4h, 6h, 8h, 12h
     * days	     1d, 3d
     * weeks	 1w
     * months	 1M
     */
    @Test
    public void givenCustomInterval_whenGetKlines_thenReturnCorrectData() throws Exception {
        // given
        when(binanceService.getKlines(symbol, Interval.INTERVAL_5m, any(), any(), any())).thenReturn(klinesItem5mDtos);

        // when
        ResultActions perform = mockMvc.perform(get("/api/prices/" + symbol + "/klines?symbol=5m"));

        //then

        perform.andExpect(status().isOk());
        perform.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<KlinesItemDto> klines = objectMapper
                .readValue(perform.andReturn().getResponse().getContentAsString(), new TypeReference<>() {});

        for (KlinesItemDto kline : klines) {
            long interval = kline.closeTime() - kline.openTime();
            assertThat(interval)
                    .as("Checking that correct interval")
                    .isBetween(299_000L, 301_000L);
        }
    }












}
