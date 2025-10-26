package by.lisovich.binance_finance_tracker.controller;

import by.lisovich.binance_finance_tracker.binance.dto.AggTradesResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.AvgPriceResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.DepthResponseDto;
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
        symbolService.findBySymbol(symbol);

        AvgPriceResponseDto avgPriceResponseDto = binanceService.retriveAvgPrice(symbol);
        return ResponseEntity.ok(avgPriceResponseDto);
    }

    /**
     * Что делает этот Endpoint
     * GET /api/v3/depth — это endpoint Binance, который возвращает глубину ордербука (order book depth).
     * 📘 Ордербук (Order Book) — это список всех заявок на покупку (bids) и продажу (asks) для определённой торговой пары (например, BTCUSDT).
     * 👉 То есть этот endpoint показывает, по каким ценам и в каких объёмах трейдеры хотят купить или продать актив.
     * {
     *   "lastUpdateId": 1027024,
     *   "bids": [
     *     ["4.00000000", "431.00000000"]
     *   ],
     *   "asks": [
     *     ["4.00000200", "12.00000000"]
     *   ]
     * }
     * bids — список заявок на покупку (цена, объём)
     * asks — список заявок на продажу (цена, объём)
     *
     * Как это используют трейдеры
     *
     * Анализ ликвидности
     * Смотрят, где сосредоточены крупные заявки — это помогает понять, где может находиться поддержка или сопротивление цены.
     *
     * Определение настроения рынка
     * Если больше заявок на покупку — рынок склонен к росту.
     * Если больше на продажу — рынок может быть под давлением.
     *
     * Алготрейдинг и боты
     * Боты постоянно запрашивают этот endpoint, чтобы видеть в реальном времени, как меняются заявки, и быстро реагировать.
     * */
    @GetMapping("/prices/{symbol}/depth")
    public ResponseEntity<DepthResponseDto> getDepth(@PathVariable String symbol, @RequestParam(defaultValue = "100") String limit) {

        DepthResponseDto dept = binanceService.getDept(symbol, Integer.valueOf(limit));

        return ResponseEntity.ok(dept);
    }


}
