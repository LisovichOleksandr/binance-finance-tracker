package by.lisovich.binance_finance_tracker.security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginUserDto {
    private String email;
    private String password;
}
