package by.lisovich.binance_finance_tracker.slice.controller;

import by.lisovich.binance_finance_tracker.binance.dto.*;
import by.lisovich.binance_finance_tracker.controller.PriceController;
import by.lisovich.binance_finance_tracker.exception.SymbolNotFoundException;
import by.lisovich.binance_finance_tracker.security.filter.JwtAuthenticationFilter;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

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
//@ExtendWith(MockitoExtension.class) Ето лишння анотация, так ка есть @WebMvcTest
public class PriceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    SymbolService symbolService;
    @MockitoBean
    PriceSnapshotService priceSnapshotService;
    @MockitoBean
    BinanceService binanceService;
    @Autowired
    ObjectMapper objectMapper;
    private AvgPriceResponseDto avgPriceResponseDto;

    @BeforeEach
    public void init() {
        avgPriceResponseDto = new AvgPriceResponseDto("BNBUSDT", "BNBUSDT", 5L, "1108.68091085", 1761356520152L);
    }

    @Test
    public void PriceController_SendRequestToBinance_ReturnAveragePrice() throws Exception {
        String symbol = "BNBUSDT";

        when(binanceService.retriveAvgPrice(symbol))
                .thenReturn(avgPriceResponseDto);

        ResultActions response = mockMvc.perform(get("/api/prices/" + symbol + "/avg")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.sSymbol").value("BNBUSDT"))
                .andExpect(jsonPath("$.price").value("1108.68091085"));

        Mockito.verify(binanceService, Mockito.times(1)).retriveAvgPrice(any());
    }

    @Test
    public void getAveragePrice_WhenSymbolInvalid_Returns404() throws Exception {
        String symbol = "INVALID";

        when(symbolService.findBySymbol(symbol))
                .thenThrow(new SymbolNotFoundException("Symbol " + symbol + " is not valid."));
        mockMvc.perform(get("/api/prices/" + symbol + "/avg"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAveragePrice_WhenBinanceFails_Return500() throws Exception {
        String symbol = "BNBUSDT";

        when(binanceService.retriveAvgPrice(symbol))
                .thenThrow(new RuntimeException("Binance API error"));

        mockMvc.perform(get("/api/prices/" + symbol + "/avg"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getDepth_SendRequestToBinance_ReturnOrderBookDept() throws Exception {
        // given
        String symbol = "BNBUSDT";
        Integer limit = 100; //default

        DepthResponseDto depthResponseDto = new DepthResponseDto(symbol, 1027024L,
                List.of(List.of("4.00000000", "431.00000000")), List.of(List.of("4.00000200", "12.00000000")));
        when(binanceService.getDept(symbol, limit)).thenReturn(depthResponseDto);

        // when
        ResultActions perform = mockMvc.perform(get("/api/prices/" + symbol + "/depth"));

        //then
        perform.andExpect(status().isOk());
        perform.andExpect(jsonPath("$.symbol").value("BNBUSDT"));
        perform.andExpect(jsonPath("$.lastUpdateId").value("1027024"));
        perform.andExpect(jsonPath("$.bids").isArray());
        perform.andExpect(jsonPath("$.bids[0]").isArray());
        perform.andExpect(jsonPath("$.bids[0][0]").value("4.00000000"));
        perform.andExpect(jsonPath("$.bids[0][1]").value("431.00000000"));
        perform.andExpect(jsonPath("$.asks[0][0]").value("4.00000200"));
        perform.andExpect(jsonPath("$.asks[0][1]").value("12.00000000"));

        verify(symbolService, times(1)).findBySymbol(symbol);
        verify(binanceService, times(1)).getDept(any(), any());
    }

    @Test
    public void getTrades_shouldSendRequestToBinance_ReturnTradesResponseDtoAnd200() throws Exception {
        String symbol = "BNBUSDT";
        Integer limitDefault = 500;
        TradesResponseDto tradesResponseDto = new TradesResponseDto(symbol,
                28457L, "4.00000100", "12.00000000", "48.000012",
                1499865549590L, true, true);

        when(binanceService.getTrades(symbol, limitDefault)).thenReturn(List.of(tradesResponseDto));

        ResultActions perform = mockMvc.perform(get("/api/prices/" + symbol + "/trades"));

        System.out.println(perform.andReturn().getResponse().getContentAsString());
        perform.andExpect(status().isOk());
        perform.andExpect(jsonPath("$").isArray());


        verify(symbolService, times(1)).findBySymbol(symbol);
        verify(binanceService, times(1)).getTrades(any(), any());
    }

    @Test
    public void givenSymbolAndParams_WhenGetHistoricalTrades_ThenReturnExpectedResponse() throws Exception {
        //given
        String symbol = "BNBUSDT";
        Integer limitDefault = 500;
        Long fromId = 1L;
        HistoricalTradesResponseDto trade1 = new HistoricalTradesResponseDto("BNBUSDT",
                28457L,"4.00000100","12.00000000","48.000012",
                1499865549590L,true,true);
        HistoricalTradesResponseDto trade2 = new HistoricalTradesResponseDto("BTCUSDT",
                51234L,"67500.50000000","0.00200000","135.00100000",
                1699865549123L,false,true);


//        doNothing().when(symbolService).findBySymbol(symbol);
        when(binanceService.getHistoricalTrades(symbol, limitDefault, fromId)).thenReturn(List.of(trade1, trade2));

        //when
        ResultActions perform = mockMvc.perform(get("/api/prices/" + symbol + "/historical-trades"));

        //then
        perform.andExpect(status().isOk());
        perform.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        perform.andExpect(jsonPath("$").isArray());
        perform.andExpect(jsonPath("$.length()").value(2));
        perform.andExpect(jsonPath("$[0].symbol").value("BNBUSDT"));
        perform.andExpect(jsonPath("$[1].qty").value("0.00200000"));

        verify(symbolService, times(1)).findBySymbol(symbol);
        verify(binanceService, times(1)).getHistoricalTrades(any(), any(), any());
    }

    @Test
    public void givenNegativeParam_WhenGetHistoricalTrades_ThenReturn400() throws Exception {
        //given
        String symbol = "BNBUSDT";
        Integer limitDefault = 500;
        Long fromId = 1L;
        HistoricalTradesResponseDto trade1 = new HistoricalTradesResponseDto("BNBUSDT",
                28457L,"4.00000100","12.00000000","48.000012",
                1499865549590L,true,true);
        HistoricalTradesResponseDto trade2 = new HistoricalTradesResponseDto("BTCUSDT",
                51234L,"67500.50000000","0.00200000","135.00100000",
                1699865549123L,false,true);


        when(binanceService.getHistoricalTrades(symbol, limitDefault, fromId)).thenReturn(List.of(trade1, trade2));

        //when
        ResultActions perform = mockMvc.perform(get("/api/prices/" + symbol + "/historical-trades")
                .param("fromId", "-4"));

        //then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidRequest_whenGetKlines_thenReturnListKlinesItemDtoAnd200() throws Exception {
        //given
        String symbol = "BNBUSDT";

        KlinesItemDto klinesItemDto = new KlinesItemDto(
                1609459200000L, "28923.63", "28950.00", "28923.63", "28930.24", "12.345",
                1609459260000L, "356789.12", 154, "6.789", "195678.44", "0"
        );

        KlinesItemDto klinesItemDto1 = new KlinesItemDto(
                1609459260000L, "28930.24", "28960.55", "28910.10", "28950.12", "10.982",
                1609459320000L, "318452.77", 143, "4.551", "132551.11", "0"
        );

        when(binanceService.getKlines(any(), any(), any(), any(), any())).thenReturn(List.of(klinesItemDto, klinesItemDto1));

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
            Assertions.assertThat(interval)
                    .as("Checking that the candle is a minute candle")
                    .isBetween(59_000L, 61_000L);
        }

        verify(binanceService, times(1)).getKlines(any(), any(), any(), any(), any());
    }














}
