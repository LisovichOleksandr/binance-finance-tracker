package by.lisovich.binance_finance_tracker.security;

import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.security.dto.LoginResponse;
import by.lisovich.binance_finance_tracker.security.dto.LoginUserDto;
import by.lisovich.binance_finance_tracker.security.dto.RegisterUserDto;
import by.lisovich.binance_finance_tracker.security.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserDto input) {
        User registeredUser = authenticationService.singUp(input);

        return ResponseEntity.ok(new UserDto(registeredUser));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto input) {
        User authenticatedUser = authenticationService.authenticate(input);

        String token = jwtService.generateToken(authenticatedUser);

        return  ResponseEntity.ok(LoginResponse.builder()
                        .token(token)
                        .expiresIn(jwtService.getExpirationTime())
                .build());

    }

}
