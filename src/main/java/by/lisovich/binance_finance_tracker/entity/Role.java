package by.lisovich.binance_finance_tracker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", unique = true)
    private String role;
}
