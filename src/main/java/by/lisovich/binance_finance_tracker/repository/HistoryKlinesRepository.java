package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.HistoryKlines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryKlinesRepository extends JpaRepository<Long, HistoryKlines> {
}
