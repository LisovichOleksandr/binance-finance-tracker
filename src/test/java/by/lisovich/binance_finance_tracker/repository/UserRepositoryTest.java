package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldInsertAndReadUser() {
        //given
        User testUser = new User(null, "testuser", "pass", "test@email.com", null, null);
        userRepository.save(testUser);

        //when
        List<User> users = userRepository.findAll();

        //then
        Assertions.assertFalse(users.isEmpty());
        Assertions.assertEquals("testuser", users.get(0).getUsername());

    }
}