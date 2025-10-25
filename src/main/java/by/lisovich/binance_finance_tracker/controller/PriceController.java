package by.lisovich.binance_finance_tracker.controller;

import by.lisovich.binance_finance_tracker.binance.dto.AggTradesResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.AvgPriceResponseDto;
import by.lisovich.binance_finance_tracker.controller.dto.PriceDto;
import by.lisovich.binance_finance_tracker.controller.dto.SymbolDto;
import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.service.BinanceService;
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
    // Сохраняет в БД
    private final PriceSnapshotService priceSnapshotService;
    // Обертка над методами библиотеки с нужними return-ами
    private final BinanceService binanceService;

    @GetMapping("/symbols")
    public ResponseEntity<List<SymbolDto>> allSymbols() {
        List<Symbol> symbols = symbolService.allSymbols();

        return ResponseEntity.ok(symbols.stream().map(SymbolDto::of).toList());
    }

    // етот ендпоинт не сохраняет ничего в репозиторий он получает и отдает
    @GetMapping("/prices/{symbol}/latest")
    public ResponseEntity<PriceDto> getLatestPrice(@PathVariable String symbol) {
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

    @GetMapping("/prices/{symbol}/avg")
    public ResponseEntity<AvgPriceResponseDto> getAveragePrice(@PathVariable String symbol) {
        AvgPriceResponseDto avgPriceResponseDto = binanceService.avgPriceDto(symbol);
        return ResponseEntity.ok(avgPriceResponseDto);
    }
}
