package app.restman.api.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RegisterUserDto {
    private String email;
    private String username;
    private String password;
}
