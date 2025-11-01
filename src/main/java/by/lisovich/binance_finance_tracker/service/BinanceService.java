package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import by.lisovich.binance_finance_tracker.binance.dto.*;
import ch.qos.logback.core.read.ListAppender;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceService {

    private final BinanceConfig binanceConfig;

    public AvgPriceResponseDto retriveAvgPrice(String symbol) {


        AvgPriceResponse data = binanceConfig.connectSpot().avgPrice(symbol).getData();

        return new AvgPriceResponseDto(symbol,symbol, data.getMins(), data.getPrice(), data.getCloseTime());
    }



    /**
     * Расшифровка полей:
     * Поле	Тип	Значение	Описание
     * a	Long	445869788	ID агрегированной сделки (aggregate tradeId) — уникальный идентификатор этой записи.
     * p	String	"278.30000000"	Цена, по которой произошла сделка.
     * q	String	"0.09700000"	Количество (quantity), объём сделки в базовой валюте (например, BNB).
     * f	Long	582795457	Первый tradeId — ID первой обычной сделки, вошедшей в эту агрегированную.
     * l	Long	582795457	Последний tradeId — ID последней обычной сделки, вошедшей в агрегированную. (Если f == l, то это одна сделка).
     * T	Long	1662076802582	Время сделки (timestamp) в миллисекундах (Unix Time).
     * m	boolean	true	Покупатель — маркет-мейкер? Если true, значит сделка была инициирована продавцом (sell order). Если false, инициатор — покупатель.
     * M	boolean	true	Не используется (всегда true в Spot API, зарезервировано для фьючерсов).
     * */
    public AggTradesResponse aggTrades(String symbol, Integer limit) {
        Long fromId = 1L;
        Long startTime = 1735693200000L;
        Long endTime = 1735693200000L;

        ApiResponse<AggTradesResponse> response = binanceConfig.connectSpot()
                .aggTrades(symbol, null, null, null, limit);

        AggTradesResponse data = response.getData();
        return data;
    }

    public BigDecimal tickerPrice(String symbol) {
        Symbols symbols = null;
        ApiResponse<TickerPriceResponse> apiResponse = binanceConfig.connectSpot().tickerPrice(symbol, symbols);
        String price = apiResponse.getData().getTickerPriceResponse1().getPrice();
        return new BigDecimal(Objects.requireNonNull(price));
    }


    public DepthResponseDto getDept(String symbol, Integer limit) {
        ApiResponse<DepthResponse> depth = binanceConfig.connectSpot().depth(symbol, limit);

        return new DepthResponseDto(symbol,
                depth.getData().getLastUpdateId(),
                depth.getData().getBids(),
                depth.getData().getAsks()
                );
    }

    public List<TradesResponseDto> getTrades(String symbol, Integer limit) {
        GetTradesResponse trades = binanceConfig.connectSpot().getTrades(symbol, limit).getData();

        return trades.stream().map(r -> new TradesResponseDto(symbol, r.getId(), r.getPrice(), r
                .getQty(), r.getQuoteQty(), r.getTime(), r.getIsBuyerMaker(), r.getIsBestMatch())).toList();
    }

    public List<HistoricalTradesResponseDto> getHistoricalTrades(String symbol, Integer limit, Long fromId) {
        ApiResponse<HistoricalTradesResponse> apiResponse = binanceConfig.connectSpot()
                .historicalTrades(symbol, limit, fromId);

        return apiResponse.getData().stream()
                .map(t -> new HistoricalTradesResponseDto(symbol,
                        t.getId(), t.getPrice(),t.getQty(), t.getQuoteQty(),
                        t.getTime(), t.getIsBuyerMaker(), t.getIsBestMatch())).toList();
    }

    public List<KlinesItemDto> getKlines(String symbol, Interval interval, Long startTime, Long endTime, Integer limit) {
        String timeZone = null;
        ApiResponse<KlinesResponse> klines = binanceConfig.connectSpot().
                klines(symbol, interval, startTime, endTime, timeZone, limit);

        List<KlinesItemDto> dtos = klines.getData().stream().map(arr -> new KlinesItemDto(
                Long.valueOf(arr.get(0)),
                arr.get(1),
                arr.get(2),
                arr.get(3),
                arr.get(4),
                arr.get(5),
                Long.valueOf(arr.get(6)),
                arr.get(7),
                Integer.valueOf(arr.get(8)),
                arr.get(9),
                arr.get(10),
                arr.get(11)
                )).toList();
        return dtos;
    }
}
