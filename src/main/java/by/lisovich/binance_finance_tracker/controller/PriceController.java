package by.lisovich.binance_finance_tracker.controller;

import by.lisovich.binance_finance_tracker.binance.BinanceServiceExamples;
import by.lisovich.binance_finance_tracker.binance.dto.AggTradesResponseDto;
import by.lisovich.binance_finance_tracker.controller.dto.PriceDto;
import by.lisovich.binance_finance_tracker.controller.dto.SymbolDto;
import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import com.binance.connector.client.spot.rest.model.AggTradesResponse;
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
    private final BinanceServiceExamples binanceService;

    @GetMapping("/symbols")
    public ResponseEntity<List<SymbolDto>> allSymbols() {
        List<Symbol> symbols = symbolService.allSymbols();

        return ResponseEntity.ok(symbols.stream().map(SymbolDto::of).toList());
    }

    @GetMapping("/prices/{symbol}/latest")
    public ResponseEntity<PriceDto> getLatestPrice(@PathVariable String symbol) {
        System.out.println("CONTROLLER getLatestPrice: ...");
        Symbol byFind = symbolService.findBySymbol(symbol);
        PriceSnapshot latestPrice = priceSnapshotService.findLatestPrice(byFind.getSymbol());
        return ResponseEntity.ok(PriceDto.of(latestPrice));
    }

    @GetMapping("/prices/{symbol}/agg-latest")
    public ResponseEntity<List<AggTradesResponseDto>> getLastAggTrades(@PathVariable String symbol,
                                                                       @RequestParam(defaultValue = "1") Integer limit) {
        AggTradesResponse aggTradesResponseInners = binanceService.aggTrades(symbol, limit);

        List<AggTradesResponseDto> listDto = AggTradesResponseDto.getListDto(symbol, symbol, aggTradesResponseInners);
        return ResponseEntity.ok(listDto);
    }

}
