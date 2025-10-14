package by.lisovich.binance_finance_tracker;


import by.lisovich.binance_finance_tracker.binance.BinanceServiceExamples;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.repository.PriceSnapshotRepository;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class BinanceExampleTester implements CommandLineRunner {

    private final BinanceServiceExamples binanceServiceExamples;
    private final SymbolRepository symbolRepository;
    private final BinanceService binanceService;
    private final PriceSnapshotService priceSnapshotService;

    @Override
    public void run(String... args) throws Exception {


    }
}
