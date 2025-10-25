package by.lisovich.binance_finance_tracker;

import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import by.lisovich.binance_finance_tracker.binance.BinanceServiceExamples;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.model.AvgPriceResponse;
import io.jsonwebtoken.Jwts;

public class TesterRunner {
    public static void main(String[] args) {

        BinanceServiceExamples binanceServiceExamples = new BinanceServiceExamples(new BinanceConfig());

        AvgPriceResponse avgPrice = binanceServiceExamples.avgPrice("BNBUSDT");

        System.out.println(avgPrice);
    }
}
