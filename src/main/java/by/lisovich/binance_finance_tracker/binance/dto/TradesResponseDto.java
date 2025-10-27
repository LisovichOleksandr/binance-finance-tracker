package by.lisovich.binance_finance_tracker.binance.dto;

/**
 *     "id": 28457,
 *     "price": "4.00000100",
 *     "qty": "12.00000000",
 *     "quoteQty": "48.000012",
 *     "time": 1499865549590,
 *     "isBuyerMaker": true,
 *     "isBestMatch": true
 * */

public record TradesResponseDto(String symbol,
                                Long id,
                                String price,
                                String qty,
                                String quoteQty,
                                Long time,
                                Boolean isBuyerMarker,
                                Boolean isBestMatch
                                ) {
}
