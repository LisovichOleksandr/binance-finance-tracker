package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.exception.SymbolNotFoundException;
import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SymbolService {

    private final SymbolRepository symbolRepository;

    public List<Symbol> allSymbols() {
        return symbolRepository.findAll();
    }

    public Symbol findBySymbol(String symbol) {
        return symbolRepository.findBySymbol(symbol)
                .orElseThrow(() -> new SymbolNotFoundException("Symbol " + symbol + " is not valid."));
    }
}
