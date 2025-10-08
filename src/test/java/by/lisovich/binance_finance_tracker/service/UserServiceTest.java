package by.lisovich.binance_finance_tracker.service;

import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Fast unit tests without (Spring Boot)
 * */

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks UserService userService;
    @Mock UserRepository userRepository;

    @Test
    void shouldReturnUserByEmail() {
        // given
        String email = "test-user@email.com";
        User embededUser = User.builder()
                .username("testUser")
                .passwordHash("HASH-PASS")
                .email(email)

                .build();
        // stab userRepository when the findById() method retrieves
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(embededUser));
        // when
        User userFromService = userService.findByEmail(email);
        //then
        assertThat(userFromService).isNotNull();
        assertThat(userFromService.getEmail()).isEqualTo(email);
        assertThat(userFromService.getUsername()).isEqualTo("testUser");
        verify(userRepository, Mockito.times(1)).findByEmail(anyString());
    }

    @Test
    void shouldSaveUserHashPassAndReturnActualId() {
        // given
        String email = "test-user@email.com";
        User embedUser = User.builder()
                .username("testUser")
                .passwordHash("pass")
                .email(email)
                .build();

        User retrievedUser = User.builder()
                .id(1L)
                .username("testUser")
                .passwordHash("hash-pass")
                .email(email)
                .build();
        when(userRepository.save(embedUser)).thenReturn(retrievedUser);

        // when
        User userFromDb = userService.save(embedUser);

        // then
        assertThat(userFromDb.getId()).isEqualTo(1L);
        assertThat(userFromDb.getUsername()).isEqualTo("testUser");
        assertThat(userFromDb.getEmail()).isEqualTo(email);
        assertThat(userFromDb.getPasswordHash()).isNotEqualTo("pass");

        verify(userRepository, times(1)).save(any(User.class));
    }
}