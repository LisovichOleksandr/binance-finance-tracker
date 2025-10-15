package by.lisovich.binance_finance_tracker.security;

import by.lisovich.binance_finance_tracker.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {
    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();

        Field secretKey = JwtService.class.getDeclaredField("secretKey");
        secretKey.setAccessible(true);
        secretKey.set(jwtService, "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b");

        Field jwtExpiration = JwtService.class.getDeclaredField("jwtExpiration");
        jwtExpiration.setAccessible(true);
        jwtExpiration.set(jwtService, 60L);

        userDetails = User.builder()
                .username("testUser")
                .passwordHash("pass")
                .email("test@email.li")
                .build();
    }

    @Test
    void shouldGenerateAndValidateTaken() {
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertEquals("test@email.li", jwtService.extractUserName(token));
    }

    @Test
    void shouldDetectInvalidUsername() {
        String token = jwtService.generateToken(userDetails);

        UserDetails anotherUser = User.builder()
                .username("wrongUser")
                .passwordHash("pass")
                .email("wrong@email.li")
                .build();

        assertFalse(jwtService.isTokenValid(token, anotherUser));
    }

    @Test
    void shouldExtractClaims() {
        String token = jwtService.generateToken(Map.of("role", "ADMIN"), userDetails);

        Claims claims = jwtService.extractAllClaims(token);

        assertEquals("test@email.li", claims.getSubject());
        assertEquals("ADMIN", claims.get("role"));
    }



























}