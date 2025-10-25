package by.lisovich.binance_finance_tracker.integration.service;

import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.repository.PriceSnapshotRepository;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceSnapshotServiceTest {

    @Mock
    BinanceService binanceService;
    @Mock SymbolRepository symbolRepository;
    @Mock PriceSnapshotRepository priceSnapshotRepository;
    @InjectMocks
    PriceSnapshotService priceSnapshotService;

    @Test
    void shouldFetchSymbolAndPriceThenSavePrice() {
        // given
        String s = "BNBUSDT";
        BigDecimal expectedPrice = new BigDecimal("123.45");
        Symbol symbol = Symbol.builder()
                .id(1L)
                .symbol(s)
                .baseAsset("BNB")
                .quoteAsset("USDT")
                .build();
        Optional<Symbol> optionalSymbol = Optional.of(symbol);
        when(symbolRepository.findBySymbol(s)).thenReturn(optionalSymbol);
        when(binanceService.tickerPrice(s)).thenReturn(expectedPrice);

        //when
        PriceSnapshot priceSnapshot = priceSnapshotService.fetchAndSavePrice(s);

        //then
        verify(symbolRepository, times(1)).findBySymbol(any());
        verify(binanceService, times(1)).tickerPrice(eq("BNBUSDT"));

        ArgumentCaptor<PriceSnapshot> captor = ArgumentCaptor.forClass(PriceSnapshot.class);
        verify(priceSnapshotRepository, times(1)).save(captor.capture());
        PriceSnapshot value = captor.getValue();
        Assertions.assertThat(value.getPrice()).isEqualTo("123.45");
    }

    @Test
    void findLatestPrice() {
        // given
        String symbol = "BNBUSDT";
        PriceSnapshot priceSnapshot = PriceSnapshot.builder()
                .price(new BigDecimal("123.45"))
                .createdAt(LocalDateTime.now())
                .build();

        when(priceSnapshotRepository.findLatestPriceBySymbol(symbol))
                .thenReturn(priceSnapshot);

        // when
        PriceSnapshot result = priceSnapshotService.findLatestPrice(symbol);

        //then
        Assertions.assertThat(result).isEqualTo(priceSnapshot);
        verify(priceSnapshotRepository, times(1))
                .findLatestPriceBySymbol(ArgumentMatchers.eq(symbol));
    }
}