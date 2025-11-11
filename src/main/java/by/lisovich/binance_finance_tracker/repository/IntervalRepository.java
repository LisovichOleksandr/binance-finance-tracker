package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.Interval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntervalRepository extends JpaRepository<Long, Interval> {
}
