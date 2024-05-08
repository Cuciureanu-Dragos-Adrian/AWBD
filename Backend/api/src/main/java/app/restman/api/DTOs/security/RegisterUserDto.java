package app.restman.api.DTOs.security;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;
}
