package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.repository.PriceSnapshotRepository;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceSnapshotServiceTest {

    @Mock BinanceService binanceService;
    @Mock SymbolRepository symbolRepository;
    @Mock PriceSnapshotRepository priceSnapshotRepository;
    @InjectMocks PriceSnapshotService priceSnapshotService;

    @Test
    void fetchAndSavePrice() {


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
        Mockito.verify(priceSnapshotRepository, Mockito.times(1))
                .findLatestPriceBySymbol(ArgumentMatchers.eq(symbol));
    }
}