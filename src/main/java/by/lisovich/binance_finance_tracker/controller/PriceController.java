package by.lisovich.binance_finance_tracker.controller;

import by.lisovich.binance_finance_tracker.binance.dto.*;
import by.lisovich.binance_finance_tracker.controller.dto.PriceDto;
import by.lisovich.binance_finance_tracker.controller.dto.SymbolDto;
import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import by.lisovich.binance_finance_tracker.service.PriceSnapshotService;
import by.lisovich.binance_finance_tracker.service.SymbolService;
import com.binance.connector.client.spot.rest.model.AggTradesResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Validated
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
     * Как это используют трейдеры
     * Анализ ликвидности
     * Смотрят, где сосредоточены крупные заявки — это помогает понять, где может находиться поддержка или сопротивление цены.
     * Определение настроения рынка
     * Если больше заявок на покупку — рынок склонен к росту.
     * Если больше на продажу — рынок может быть под давлением.
     * Алготрейдинг и боты
     * Боты постоянно запрашивают этот endpoint, чтобы видеть в реальном времени, как меняются заявки, и быстро реагировать.
     * */
    @GetMapping("/prices/{symbol}/depth")
    public ResponseEntity<DepthResponseDto> getDepth(@PathVariable String symbol, @RequestParam(defaultValue = "100") String limit) {
        // перевірка валідності символа.
//        викидає помилку SymbolNotFoundException(), яка обробляється глобальним handleSymbolNotFoundException
        symbolService.findBySymbol(symbol);

        DepthResponseDto dept = binanceService.getDept(symbol, Integer.valueOf(limit));

        return ResponseEntity.ok(dept);
    }

/**
 * 🧩 1. Что делает этот метод
 * Метод
 * getApi().getTrades(symbol, limit);
 * обращается к Binance Spot REST API по эндпоинту:
 * GET /api/v3/trades
 * и возвращает список последних сделок (trades) по заданной торговой паре, например "BNBUSDT".
 * Это публичный эндпоинт — не требует авторизации.
 * ⚙️ 2. Что возвращает метод
 * Ответ API — это JSON-массив из объектов, каждый из которых представляет реальную торговую сделку, совершённую на спотовом рынке Binance.
 * Пример:
 * [
 *   {
 *     "id": 28457,
 *     "price": "4.00000100",
 *     "qty": "12.00000000",
 *     "quoteQty": "48.000012",
 *     "time": 1499865549590,
 *     "isBuyerMaker": true,
 *     "isBestMatch": true
 *   }
 * ]
 * Расшифровка полей:
 * Поле	Значение
 * id	Уникальный идентификатор сделки
 * price	Цена, по которой сделка совершена
 * qty	Количество базовой валюты (например, BNB)
 * quoteQty	Количество котируемой валюты (например, USDT)
 * time	Время сделки (в миллисекундах UNIX)
 * isBuyerMaker	true, если ордер покупателя был maker (он стоял в стакане), false, если taker
 * isBestMatch	Указывает, была ли сделка исполнена по наилучшей доступной цене
 * 🧠 3. Где используют эти данные
 * Эти данные используются в трёх основных направлениях:
 * 🔹 A. Визуализация рынка (Market Data)
 * Построение ленты сделок (trade tape) — отображение последних сделок в реальном времени.
 * Пример: в интерфейсе Binance справа от графика, где бегут красные/зелёные строчки.
 * Визуализация активности рынка:
 * когда сделки идут часто и с большим объёмом — рынок активен; когда редко — низкая ликвидность.
 * 🔹 B. Аналитика и индикаторы
 * Трейдеры и алгоритмы используют данные getTrades для:
 * Расчёта среднего объёма сделок за последние N минут;
 * Определения преобладания покупателей или продавцов:
 * если isBuyerMaker = true чаще — значит, продавцы активнее, цена может падать;
 * Поиска всплесков объёмов (volume spikes) — сигнал о возможном развороте или пробое;
 * Анализа импульсов — последовательности сделок в одном направлении.
 * 🔹 C. Алготрейдинг и бэктестинг
 * Для торговых ботов данные getTrades служат:
 *
 * Источником реальных сделок для анализа микродвижений рынка;
 * Вводом для моделей машинного обучения (например, прогноз краткосрочного движения);
 * Проверкой эффективности стратегий (бэктестинг);
 * Синхронизацией симулятора торговли с реальными условиями рынка.
 * 📈 4. Чем полезны данные трейдерам
 * Польза	Пример
 * Измерить рыночную активность	Частые сделки → высокая ликвидность
 * Определить давление покупателей/продавцов	Преобладание сделок от покупателей может означать рост
 * Следить за реакцией на новости	После новости об обновлении сети — резко увеличиваются сделки
 * Оценить глубину и скорость изменений	Если сделки идут по всем ценам быстро — волатильность повышается
 * 🔍 5. Как трейдер делает выводы
 * Например, трейдер анализирует поток данных getTrades и замечает:
 * За 10 секунд — 200 сделок подряд, большинство isBuyerMaker=false
 * (т.е. покупатели активно исполняют по рынку).
 * ➜ это бычий импульс, возможен краткосрочный рост цены.
 * Если же сделки в основном isBuyerMaker=true, и цена постепенно снижается —
 * ➜ рынок медвежий, идёт сброс позиций.
 * 💻 6. Как используют на практике (пример в коде)
 * Пример простого анализа:
 * ApiResponse<GetTradesResponse> response = getApi().getTrades("BTCUSDT", 1000);
 * List<Trade> trades = response.getData().getTrades();
 * long buyCount = trades.stream().filter(t -> !t.isBuyerMaker()).count();
 * long sellCount = trades.stream().filter(Trade::isBuyerMaker).count();
 * if (buyCount > sellCount * 1.2) {
 *     System.out.println("Покупатели доминируют — рынок может расти");
 * } else if (sellCount > buyCount * 1.2) {
 *     System.out.println("Продавцы доминируют — рынок может снижаться");
 * } else {
 *     System.out.println("Баланс сил — боковой тренд");
 * }
 * 📊 7. Ограничения
 * Возвращает максимум 1000 сделок;
 * Только недавние сделки, без истории за прошлые дни;
 * Для исторических данных используется другой эндпоинт — /api/v3/historicalTrades.
 * 🧭 8. Итого
 * Параметр	Значение
 * Метод	GET /api/v3/trades
 * Назначение	Получить последние сделки на рынке
 * Используется для	Анализа активности, потока ордеров, силы покупателей/продавцов
 * Тип данных	Реальные рыночные сделки
 * Применение	Алготрейдинг, аналитика, визуализация, сигналы
 * Выводы	Позволяет оценить краткосрочную динамику и настроения рынка
 * Хочешь, я покажу, как можно визуализировать поток сделок (getTrades) в виде «ленты» или графика в Java (например, с помощью Swing или JavaFX)?
 * Так будет видно, как трейдер "чувствует" рынок через эти данные.
 * */
    @GetMapping("/prices/{symbol}/trades")
    public ResponseEntity<List<TradesResponseDto>> getTrades(@PathVariable String symbol,
                                                             @RequestParam(defaultValue = "500") String limit) {
        symbolService.findBySymbol(symbol);

        List<TradesResponseDto> trades = binanceService.getTrades(symbol, Integer.valueOf(limit));

        return ResponseEntity.ok(trades);
    }

    @GetMapping("/prices/{symbol}/historical-trades")
    public ResponseEntity<List<HistoricalTradesResponseDto>> getHistoricalTrades(@PathVariable String symbol,
                                                                                 @RequestParam(defaultValue = "500") @Min(1) @Max(1000) Integer limit,
                                                                                 @RequestParam(defaultValue = "1") @Positive Long fromId) {
        symbolService.findBySymbol(symbol);
        List<HistoricalTradesResponseDto> historicalTrades = binanceService.getHistoricalTrades(symbol, limit, fromId);

        return ResponseEntity.ok(historicalTrades);
    }

}
