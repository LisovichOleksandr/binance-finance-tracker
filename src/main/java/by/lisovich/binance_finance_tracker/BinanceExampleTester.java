package by.lisovich.binance_finance_tracker;


import by.lisovich.binance_finance_tracker.binance.BinanceServiceExamples;
import by.lisovich.binance_finance_tracker.entity.Interval;
import by.lisovich.binance_finance_tracker.entity.Role;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.repository.*;
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

    private final IntervalRepository intervalRepository;
    private final HistoryKlinesRepository historyKlinesRepository;


    @Override
    public void run(String... args) throws Exception {

        List<Interval> all = intervalRepository.findAll();
        System.out.println("*******> GEt Interval s >>");
//        System.out.println(all);


    }
    }
