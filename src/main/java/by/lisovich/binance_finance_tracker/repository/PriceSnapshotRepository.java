package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Long> {
}
