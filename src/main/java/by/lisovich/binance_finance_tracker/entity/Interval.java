package by.lisovich.binance_finance_tracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_interval")
@Data
public class Interval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false, unique = true, length = 10)
    private CandleInterval period;
}
