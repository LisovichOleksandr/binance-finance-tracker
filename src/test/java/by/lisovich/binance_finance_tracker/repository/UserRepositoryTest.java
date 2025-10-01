package by.lisovich.binance_finance_tracker.repository;

import by.lisovich.binance_finance_tracker.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void shouldInsertAndReadUser() {
//        //given
//        User testUser = new User(null, "testuser", "pass", "test@email.com", null, null);
//        userRepository.save(testUser);
//
//        //when
//        List<User> users = userRepository.findAll();
//
//        //then
//        Assertions.assertFalse(users.isEmpty());
//        List<User> testuser = users.stream().filter(user -> user.getUsername().equals("testuser")).collect(Collectors.toList());
//        Assertions.assertEquals("testuser", testuser.get(0).getUsername());
        //TODO Rewrite this test. Sawed but don`t delete after test.
    }
}