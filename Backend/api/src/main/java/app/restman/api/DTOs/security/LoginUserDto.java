package app.restman.api.DTOs.security;

import lombok.*;

@Setter
@Getter
public class LoginUserDto {
    private String email;
    private String password;
}