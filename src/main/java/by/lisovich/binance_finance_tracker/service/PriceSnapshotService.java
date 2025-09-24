package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.repository.OrderRepository;
import by.lisovich.binance_finance_tracker.repository.PriceSnapshotRepository;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PriceSnapshotService {

    private final PriceSnapshotRepository priceSnapshotRepository;
    private final SymbolRepository symbolRepository;
    private final BinanceService binanceService;

    @Transactional
    public PriceSnapshot fetchAndSavePrice(String symbol) {
        Symbol bySymbol = symbolRepository.findBySymbol(symbol)
                .orElseThrow(() -> new IllegalStateException("Unknown symbol: " + symbol));
        PriceSnapshot price = PriceSnapshot.builder()
                .symbol(bySymbol)
                .price(binanceService.tickerPrice(symbol))
                .createdAt(LocalDateTime.now())
                .build();
        return priceSnapshotRepository.save(price);
    }
}
