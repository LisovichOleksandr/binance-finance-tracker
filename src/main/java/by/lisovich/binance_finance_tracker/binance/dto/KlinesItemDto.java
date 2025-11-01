package by.lisovich.binance_finance_tracker.binance.dto;

public record KlinesItemDto(
        Long openTime,
        String open,
        String high,
        String low,
        String close,
        String volume,
        Long closeTime,
        String quoteAssetVolume,
        Integer numberOfTrades,
        String takerBuyBaseAssetVolume,
        String takerBuyQuoteAssetVolume,
        String ignore
) {}
