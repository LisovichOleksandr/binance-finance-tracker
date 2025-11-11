package by.lisovich.binance_finance_tracker.entity;

import by.lisovich.binance_finance_tracker.entity.converter.CandleIntervalConverter;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_interval")
@Data
public class Interval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period", nullable = false, unique = true, length = 10)
    @Convert(converter = CandleIntervalConverter.class)
    private CandleInterval period;
}
