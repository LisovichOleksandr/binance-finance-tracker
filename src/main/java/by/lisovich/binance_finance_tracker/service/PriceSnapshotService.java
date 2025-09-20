package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.repository.OrderRepository;
import by.lisovich.binance_finance_tracker.repository.PriceSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceSnapshotService {

    private final PriceSnapshotRepository priceSnapshotRepository;

}
