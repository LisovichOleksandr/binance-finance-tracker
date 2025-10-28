package by.lisovich.binance_finance_tracker.binance.dto;

public record HistoricalTradesResponseDto(
        String symbol,
        Long id,
        String price,
        String qty,
        String quoteQty,
        Long time,
        Boolean isBuyerMaker,
        Boolean isBestMatch
) {}
