package by.lisovich.binance_finance_tracker.controller;

import by.lisovich.binance_finance_tracker.controller.dto.SymbolDto;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PriceController {

    private final SymbolService symbolService;

    @GetMapping("/symbols")
    public ResponseEntity<List<SymbolDto>> allSymbols() {
        List<Symbol> symbols = symbolService.allSymbols();

        return ResponseEntity.ok(symbols.stream().map(SymbolDto::of).toList());
    }

}
