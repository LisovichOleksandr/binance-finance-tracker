package by.lisovich.binance_finance_tracker.integration.repository;

import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired private UserRepository userRepository;
    @Autowired private Flyway flyway;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void cleanAndMigration() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void checkDataSource() throws Exception {
        System.out.println(">>> DS = " + dataSource.getConnection().getMetaData().getURL());
    }

    @Test
    void UserRepository_findByEmail_ReturnUser() {
        //given
        User testUser = User.builder()
                .username("testuser")
                .passwordHash("pass")
                .email("test@email.com")
                .build();
        userRepository.save(testUser);

        //when
        User user = userRepository.findByEmail("test@email.com").orElseThrow();

        //then
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test@email.com");
        assertThat(user.getFirstName()).isEqualTo("testuser");
        assertThat(user.getPasswordHash()).isEqualTo("pass");
    }

    @Test
    void UserRepository_SaveAll_ReturnSavedUser() {
        //given
        User testUser = User.builder()
                .email("test@email.com")
                .username("testuser")
                .passwordHash("pass").build();

        //when
        User savedUser = userRepository.save(testUser);

        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(savedUser.getUsername()).isEqualTo("test@email.com");
    }
}