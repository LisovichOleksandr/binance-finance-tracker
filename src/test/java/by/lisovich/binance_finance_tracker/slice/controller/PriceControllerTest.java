package by.lisovich.binance_finance_tracker.slice.controller;

import by.lisovich.binance_finance_tracker.binance.dto.AvgPriceResponseDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.sSymbol").value("BNBUSDT"))
                .andExpect(jsonPath("$.price").value("1108.68091085"));

        Mockito.verify(binanceService, Mockito.times(1)).retriveAvgPrice(any());
    }

    @Test
    public void GetAveragePrice_WhenSymbolInvalid_Returns404() throws Exception {
        String symbol = "INVALID";

        when(symbolService.findBySymbol(symbol))
                .thenThrow(new SymbolNotFoundException("Symbol " + symbol + " is not valid."));
        mockMvc.perform(get("/api/prices/" + symbol + "/avg"))
                .andExpect(status().isNotFound());
    }
}
