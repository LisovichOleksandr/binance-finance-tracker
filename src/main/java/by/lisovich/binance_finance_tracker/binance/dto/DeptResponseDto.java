package by.lisovich.binance_finance_tracker.binance.dto;

import java.util.List;

public record DeptResponseDto(String symbol, Long lastUpdateId, List<List<String>> bids, List<List<String>> asks) {
}
