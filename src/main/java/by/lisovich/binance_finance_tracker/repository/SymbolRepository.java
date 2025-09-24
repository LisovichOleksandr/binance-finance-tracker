package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SymbolRepository extends JpaRepository<Symbol, Long> {

    Optional<Symbol> findBySymbol(String symbol);
}
