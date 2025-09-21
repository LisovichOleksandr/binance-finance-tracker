package by.lisovich.binance_finance_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Зберігає знімки ціни на певний момент
 * */

@Entity
@Table(name = "prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol_id")
    private Symbol symbol;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
