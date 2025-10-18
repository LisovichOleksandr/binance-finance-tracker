package by.lisovich.binance_finance_tracker.security;

import by.lisovich.binance_finance_tracker.entity.Role;
import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.repository.RoleRepository;
import by.lisovich.binance_finance_tracker.repository.UserRepository;
import by.lisovich.binance_finance_tracker.security.dto.LoginUserDto;
import by.lisovich.binance_finance_tracker.security.dto.RegisterUserDto;
import by.lisovich.binance_finance_tracker.service.RoleService;
import by.lisovich.binance_finance_tracker.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public User singUp(RegisterUserDto input) {
        Role role = roleService.findByRole("ROLE_USER");
        User user = User.builder()
                .username(input.getFullName())
                .email(input.getEmail())
                .passwordHash(passwordEncoder.encode(input.getPassword()))
                .roles(Set.of(role))
                .build();
        return userService.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword())
        );
        return userService.findByEmail(input.getEmail());
    }
}
