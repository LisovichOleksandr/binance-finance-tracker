package by.lisovich.binance_finance_tracker.slice.controller;

import by.lisovich.binance_finance_tracker.binance.dto.AvgPriceResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.DepthResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.HistoricalTradesResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.TradesResponseDto;
import by.lisovich.binance_finance_tracker.controller.PriceController;
import by.lisovich.binance_finance_tracker.exception.SymbolNotFoundException;
import by.lisovich.binance_finance_tracker.security.filter.JwtAuthenticationFilter;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void getHistoricalTradesEndpoint_ShouldReturnExpectedResponse() throws Exception {
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
        perform.andExpect(jsonPath("$").isArray());
//        perform.andExpect(jsonPath())

        verify(symbolService, times(1)).findBySymbol(symbol);
        verify(binanceService, times(1)).getHistoricalTrades(any(), any(), any());
    }



































}
