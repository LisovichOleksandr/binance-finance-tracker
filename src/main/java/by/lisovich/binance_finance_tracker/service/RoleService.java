package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.entity.Role;
import by.lisovich.binance_finance_tracker.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;


    public Role findByRole(String userRole) {
        return roleRepository.findByRole(userRole).orElseThrow(() -> new RuntimeException("Role " + userRole + " is not exist."));
    }
}
