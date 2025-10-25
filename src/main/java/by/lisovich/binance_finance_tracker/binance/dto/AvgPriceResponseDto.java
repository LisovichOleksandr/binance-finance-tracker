package by.lisovich.binance_finance_tracker.binance.dto;

import com.binance.connector.client.spot.rest.model.AvgPriceResponse;

public record AvgPriceResponseDto(String sSymbol, String bSymbol, Long mins, String price, Long closeTime) {

    public static AvgPriceResponseDto getDto(String sSymbol, String bSymbol, AvgPriceResponse avgPriceResponse) {
        return new AvgPriceResponseDto(sSymbol,
                bSymbol,
                avgPriceResponse.getMins(),
                avgPriceResponse.getPrice(),
                avgPriceResponse.getCloseTime());
    }
}
