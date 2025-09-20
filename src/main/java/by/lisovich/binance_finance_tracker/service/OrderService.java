package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.repository.OrderRepository;
import by.lisovich.binance_finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

}
