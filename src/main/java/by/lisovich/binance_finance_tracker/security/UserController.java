package by.lisovich.binance_finance_tracker.security;

import by.lisovich.binance_finance_tracker.entity.User;
import by.lisovich.binance_finance_tracker.security.dto.UserDto;
import by.lisovich.binance_finance_tracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.printf("Principal" + authentication.getPrincipal());
        System.out.printf("Principal class" + authentication.getPrincipal().getClass());
        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return ResponseEntity.ok(new UserDto(user));
        } else {
            return ResponseEntity.ok("unexpected principal type" + principal.getClass());
        }
    }

    @GetMapping("/u")
    public ResponseEntity<List<UserDto>> AllUsers() {
        List<UserDto> users = userService.getAllUsers().stream().map(UserDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
}
