package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.repository.SymbolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SymbolService {

    private final SymbolRepository symbolRepository;

}
