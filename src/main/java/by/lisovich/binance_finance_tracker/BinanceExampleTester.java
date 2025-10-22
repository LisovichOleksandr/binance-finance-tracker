package by.lisovich.binance_finance_tracker;


import by.lisovich.binance_finance_tracker.binance.BinanceServiceExamples;
import by.lisovich.binance_finance_tracker.entity.Role;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.repository.PriceSnapshotRepository;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import by.lisovich.binance_finance_tracker.repository.UserRepository;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import com.binance.connector.client.spot.rest.api.AccountApi;
import com.binance.connector.client.spot.rest.model.AggTradesResponse;
import com.binance.connector.client.spot.rest.model.AggTradesResponseInner;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class BinanceExampleTester implements CommandLineRunner {

    private final BinanceServiceExamples binanceService;

    @Override
    public void run(String... args) throws Exception {

//        AggTradesResponse response = binanceService.aggTrades();

//        for (AggTradesResponseInner aggTradesResponseInner : response) {
//            System.out.println("Id = "+aggTradesResponseInner);
        }
    }
