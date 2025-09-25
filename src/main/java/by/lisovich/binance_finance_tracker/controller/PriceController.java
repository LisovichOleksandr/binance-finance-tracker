package by.lisovich.binance_finance_tracker.controller;

import by.lisovich.binance_finance_tracker.controller.dto.PriceDto;
import by.lisovich.binance_finance_tracker.controller.dto.SymbolDto;
import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PriceController {

    private final SymbolService symbolService;
    private final PriceSnapshotService priceSnapshotService;

    @GetMapping("/symbols")
    public ResponseEntity<List<SymbolDto>> allSymbols() {
        List<Symbol> symbols = symbolService.allSymbols();

        return ResponseEntity.ok(symbols.stream().map(SymbolDto::of).toList());
    }

    @GetMapping("/prices/{symbol}/latest")
    public ResponseEntity<PriceDto> getLatestPrice(@PathVariable String symbol) {
        Symbol byFind = symbolService.findBySymbol(symbol);
        PriceSnapshot latestPrice = priceSnapshotService.findLatestPrice(byFind.getSymbol());
        return ResponseEntity.ok(PriceDto.of(latestPrice));
    }

}
