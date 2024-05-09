package app.restman.api.DTOs;

import lombok.*;

@Setter
@Getter
public class LoginUserDto {
    private String email;
    private String password;
}