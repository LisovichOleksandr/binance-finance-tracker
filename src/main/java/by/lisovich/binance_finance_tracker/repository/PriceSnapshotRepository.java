package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Long> {

    @Query(value = "SELECT p.* " +
            "FROM prices p " +
            "JOIN symbols s ON p.symbol_id = s.id " +
            "WHERE s.symbol = :symbol " +
            "ORDER BY p.created_at DESC " +
            "LIMIT 1",
            nativeQuery = true)
    PriceSnapshot findLatestPriceBySymbol(@Param("symbol") String symbol);
}
