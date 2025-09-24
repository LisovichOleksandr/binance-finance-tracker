package by.lisovich.binance_finance_tracker.controller.dto;

import by.lisovich.binance_finance_tracker.entity.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymbolDto {

    private Long id;
    private String symbol;
    private String baseAsset;
    private String quoteAsset;

    public static SymbolDto of(Symbol symbol){
        return new SymbolDto(symbol.getId(), symbol.getSymbol(), symbol.getBaseAsset(), symbol.getQuoteAsset());
    }
}
