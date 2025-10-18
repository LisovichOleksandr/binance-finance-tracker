package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
}
