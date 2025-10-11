package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User is not exist"));
    }

    public User save(User embedUser) {
        return userRepository.save(embedUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
