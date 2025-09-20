package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.Order;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
