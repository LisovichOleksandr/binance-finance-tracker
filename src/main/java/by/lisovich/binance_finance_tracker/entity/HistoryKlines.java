package by.lisovich.binance_finance_tracker.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Data
public class HistoryKlines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Interval interval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private Symbol symbol;

    @Column(name = "open_time")
    private LocalDateTime openTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "open_price", precision = 18, scale = 8)
    private BigDecimal openPrice;

    @Column(name = "high_price", precision = 18, scale = 8)
    private BigDecimal highPrice;

    @Column(name = "low_price", precision = 18, scale = 8)
    private BigDecimal lowPrice;

    @Column(name = "close_price", precision = 18, scale = 8)
    private BigDecimal closePrice;

    @Column(name = "volume", precision = 18, scale = 8)
    private BigDecimal volume;


}
