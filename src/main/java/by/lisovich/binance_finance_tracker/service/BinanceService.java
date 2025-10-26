package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import by.lisovich.binance_finance_tracker.binance.dto.AvgPriceResponseDto;
import by.lisovich.binance_finance_tracker.binance.dto.DeptResponseDto;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

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


    public DeptResponseDto getDept(String symbol, Integer limit) {
        ApiResponse<DepthResponse> depth = binanceConfig.connectSpot().depth(symbol, limit);

        return new DeptResponseDto(symbol,
                depth.getData().getLastUpdateId(),
                depth.getData().getBids(),
                depth.getData().getAsks()
                );
    }
}
