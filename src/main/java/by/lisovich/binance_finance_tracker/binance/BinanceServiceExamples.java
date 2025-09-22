package by.lisovich.binance_finance_tracker.binance;

import com.binance.connector.client.common.ApiException;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.common.configuration.ClientConfiguration;
import com.binance.connector.client.common.configuration.SignatureConfiguration;
import com.binance.connector.client.spot.rest.SpotRestApiUtil;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BinanceServiceExamples {

    private final BinanceConfig binanceConfig;

    public SpotRestApi connectSpot() {
        ClientConfiguration clientConfiguration = SpotRestApiUtil.getClientConfiguration();
        SignatureConfiguration signatureConfiguration = new SignatureConfiguration();
        signatureConfiguration.setApiKey(binanceConfig.getApiKey());
        signatureConfiguration.setSecretKey(binanceConfig.getSecretKey());
        clientConfiguration.setSignatureConfiguration(signatureConfiguration);
        return new SpotRestApi(clientConfiguration);
    }


    public void depthExample() {
        String symbol = "BNBUSDT";
        Integer limit = 500;

        ApiResponse<DepthResponse> depth = connectSpot().depth(symbol, limit);
        ApiResponse<AvgPriceResponse> avgPrice = connectSpot().avgPrice(symbol);

        System.out.println(depth.getData());
        System.out.println(avgPrice.getData());
    }

    /**
     * В Binance API klines() — это метод для получения свечных данных (candle data, или OHLC — Open, High, Low, Close)
     * по конкретной торговой паре.
     * То есть ты получаешь исторические данные о цене, объёме и времени за определённые интервалы.
     * */
    public void klinesExample() throws ApiException {
        String symbol = "BNBUSDT";
        Interval interval = Interval.INTERVAL_1s;
        Long startTime = 1735693200000L;
        Long endTime = 1735693200000L;
        String timeZone = null;
        Integer limit = 500;
        ApiResponse<KlinesResponse> response =
                connectSpot().klines(symbol, interval, startTime, endTime, timeZone, limit);
        System.out.println(response.getData());
    }


    /**
     * ticker24hr() - получить статистику изменения цены за последние 24 часа для конкретной пары.
     * */
    public void ticker24hrExample() throws ApiException {
        String symbol = "BNBUSDT";
        Symbols symbols = null;
        TickerType type = TickerType.FULL;
        ApiResponse<Ticker24hrResponse> response = connectSpot().ticker24hr(symbol, symbols, type);
        System.out.println(response.getData());
    }

    /**
    * лучшие цены и объёмы в стакане ордеров (best bid/ask) для выбранной пары.
    * т.е. tickerBookTicker() - данные о текущем лучшем покупателе и продавце
    * */
    public void tickerBookTickerExample() throws ApiException {
        String symbol = "BNBUSDT";
        Symbols symbols = null;
        ApiResponse<TickerBookTickerResponse> response = connectSpot().tickerBookTicker(symbol, symbols);
        System.out.println(response.getData());
    }


    /**
     * Смысл метода:
     * Показать, как с помощью клиента запросить «тикер» для BNB/USDT с учётом скользящего окна (1 минута)
     * и вывести полную статистику о движении цены.
     * */
    public void tickerExample() throws ApiException {
        String symbol = "BNBUSDT";
        Symbols symbols = null;
        WindowSize windowSize = WindowSize.WINDOW_SIZE_1m;
        TickerType type = TickerType.FULL;
        ApiResponse<TickerResponse> response = connectSpot().ticker(symbol, symbols, windowSize, type);
        System.out.println(response.getData());
    }

    /**
     * получить текущую цену (Last Price) для заданной торговой пары, без всякой дополнительной статистики.
     * */
    public void tickerPriceExample() throws ApiException {
        String symbol = "BNBUSDT";
        Symbols symbols = null;
        ApiResponse<TickerPriceResponse> response = connectSpot().tickerPrice(symbol, symbols);
        System.out.println(response.getData());
    }

    /**
     * tickerTradingDay() - олучить статистику торгов за текущие календарные сутки для выбранной пары.
     * */
    public void tickerTradingDayExample() throws ApiException {
        String symbol = "BNBUSDT";
        Symbols symbols = null;
        String timeZone = "";
        TickerType type = TickerType.FULL;
        ApiResponse<TickerTradingDayResponse> response =
                connectSpot().tickerTradingDay(symbol, symbols, timeZone, type);
        System.out.println(response.getData());
    }

    /**
     * uiKlines() - запросить график свечей (K-lines) по выбранной паре.
     * Он используется, чтобы получить исторические данные по свечам в удобном для визуализации виде
     * (например, для графиков на сайте Binance).
     * */
    public void uiKlinesExample() throws ApiException {
        String symbol = "BNBUSDT";
        Interval interval = Interval.INTERVAL_1s;
        Long startTime = 1735693200000L;
        Long endTime = 1735693200000L;
        String timeZone = null;
        Integer limit = 500;
        ApiResponse<UiKlinesResponse> response =
                connectSpot().uiKlines(symbol, interval, startTime, endTime, timeZone, limit);
        System.out.println(response.getData());
    }
}
