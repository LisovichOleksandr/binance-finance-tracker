package by.lisovich.binance_finance_tracker;


import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import by.lisovich.binance_finance_tracker.binance.dto.KlinesItemDto;
import by.lisovich.binance_finance_tracker.service.BinanceService;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.GetTradesResponse;
import com.binance.connector.client.spot.rest.model.HistoricalTradesResponseInner;
import com.binance.connector.client.spot.rest.model.Interval;
import com.binance.connector.client.spot.rest.model.KlinesResponse;

import java.util.List;

public class TesterRunner {
    public static void main(String[] args) {

        BinanceConfig binanceConfig = new BinanceConfig();
        BinanceService binanceService = new BinanceService(binanceConfig);

        List<KlinesItemDto> bnbusdt = binanceService.getKlines(
                "BNBUSDT",
                Interval.INTERVAL_1m,
                System.currentTimeMillis() - 3600_000L,
                System.currentTimeMillis(),
                100
        );

        System.out.println(bnbusdt);
    }
}
