package by.lisovich.binance_finance_tracker.controller;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void shouldReturn200AndNotEmpty() throws Exception {
        this.mockMvc.perform(get("/api/symbols")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(Matchers.greaterThan(0)));
    }

    @Test
    void getLatestPrice() {
    }
}

//        [{"id":1,"symbol":"BTCUSDT","baseAsset":"BTC","quoteAsset":"USDT"},
//        {"id":2,"symbol":"ETHUSDT","baseAsset":"ETH","quoteAsset":"USDT"},
//        {"id":3,"symbol":"BNBUSDT","baseAsset":"BNB","quoteAsset":"USDT"},
//        {"id":4,"symbol":"XRPUSDT","baseAsset":"XRP","quoteAsset":"USDT"},
//        {"id":5,"symbol":"ADAUSDT","baseAsset":"ADA","quoteAsset":"USDT"},
//        {"id":6,"symbol":"DOGEUSDT","baseAsset":"DOGE","quoteAsset":"USDT"},
//        {"id":7,"symbol":"SOLUSDT","baseAsset":"SOL","quoteAsset":"USDT"},
//        {"id":8,"symbol":"MATICUSDT","baseAsset":"MATIC","quoteAsset":"USDT"},
//        {"id":9,"symbol":"DOTUSDT","baseAsset":"DOT","quoteAsset":"USDT"},
//        {"id":10,"symbol":"LTCUSDT","baseAsset":"LTC","quoteAsset":"USDT"}]