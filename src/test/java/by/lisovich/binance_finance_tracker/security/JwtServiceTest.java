package by.lisovich.binance_finance_tracker.security;

import by.lisovich.binance_finance_tracker.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Date;
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

    @Test
    void shouldThrownExceptionOnInvalidToken() {
        String invalidToken = "asd.erw.fdg";

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> jwtService.extractAllClaims(invalidToken));

        assertTrue(runtimeException.getMessage().contains("Can`t parse JWT"));
    }

    @Test
    void shouldDetectExpiredToken() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field jwtExpiration = JwtService.class.getDeclaredField("jwtExpiration");
        jwtExpiration.setAccessible(true);
        jwtExpiration.set(jwtService, 1L);

        String validToken = jwtService.generateToken(userDetails);
        Claims claims = jwtService.extractAllClaims(validToken);

        Date expiredDate = new Date(System.currentTimeMillis() - 1000);

        Method getSignedKeyMethod = jwtService.getClass().getDeclaredMethod("getSignedKey");
        getSignedKeyMethod.setAccessible(true);

        Key secretKey = (Key) getSignedKeyMethod.invoke(jwtService);

        String expiredToken = Jwts.builder()
                .subject(claims.getSubject())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();


        assertTrue(jwtService.isTokenExpired(expiredToken));
    }


























}