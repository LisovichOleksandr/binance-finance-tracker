package by.lisovich.binance_finance_tracker.scheduler;

import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PriceUpdateScheduler {
    private final PriceSnapshotService priceSnapshotService;
    private final SymbolRepository symbolRepository;

    @Scheduled(fixedRateString = "${prices.snapshot.rate:60000}" )
    public void collectPrice() {
        List<Symbol> symbols = symbolRepository.findAll();
        for (Symbol s : symbols) {
            try {
                priceSnapshotService.fetchAndSavePrice(s.getSymbol());
                log.info("Price saved for {}", s.getSymbol());
            } catch (Exception e) {
                log.error("Failed to fetch price for {}", s.getSymbol(), e);
            }
        }
    }
}
