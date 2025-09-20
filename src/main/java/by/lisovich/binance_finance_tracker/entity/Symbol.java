package by.lisovich.binance_finance_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Валютні пари (торгові пари)
 * Наприклад, BTCUSDT, ETHUSDT
 * */

@Entity
@Table(name = "symbols")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", unique = true, nullable = false)
    private String symbol;

    @Column(name = "base_asset", nullable = false)
    private String baseAsset;

    @Column(name = "quote_asset", nullable = false)  // quote - Указывать цену, назначать стоимость (котировать)
    private String quoteAsset;


}
