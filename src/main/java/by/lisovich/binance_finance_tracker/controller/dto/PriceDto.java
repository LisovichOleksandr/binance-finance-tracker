package by.lisovich.binance_finance_tracker.controller.dto;

import by.lisovich.binance_finance_tracker.entity.PriceSnapshot;
import by.lisovich.binance_finance_tracker.entity.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PriceDto {

    private Long id;
    private SymbolDto symbol;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public static PriceDto of(PriceSnapshot price) {
        return new PriceDto(price.getId(), SymbolDto.of(price.getSymbol()), price.getPrice(), price.getCreatedAt());
    }
}
