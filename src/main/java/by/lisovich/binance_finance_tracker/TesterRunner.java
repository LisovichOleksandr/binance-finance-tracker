package by.lisovich.binance_finance_tracker;


import by.lisovich.binance_finance_tracker.binance.BinanceConfig;
import com.binance.connector.client.common.ApiResponse;
import com.binance.connector.client.spot.rest.api.SpotRestApi;
import com.binance.connector.client.spot.rest.model.GetTradesResponse;
import com.binance.connector.client.spot.rest.model.HistoricalTradesResponseInner;

import java.util.List;

public class TesterRunner {
    public static void main(String[] args) {

        BinanceConfig binanceConfig = new BinanceConfig();
        SpotRestApi spotRestApi = binanceConfig.connectSpot();

        ApiResponse<GetTradesResponse> response = spotRestApi.getTrades("BNBUSDT", 1000);
        GetTradesResponse data = response.getData();


        long buyCount = data.stream().filter(t -> !t.getIsBuyerMaker()).count();
        long sellCount = data.stream().filter(t -> t.getIsBuyerMaker()).count();
        System.out.println("buy: " + buyCount + "\nsell: " + sellCount);

        if (buyCount > sellCount * 1.2) {
            System.out.println("Покупатели доминируют — рынок может расти");
        } else if (buyCount < sellCount * 1.2) {
            System.out.println("Продавцы доминируют — рынок может снижаться");
        } else {
            System.out.println("Баланс сил — боковой тренд");
        }


    }
}
