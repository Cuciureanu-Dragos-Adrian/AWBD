package app.restman.api.DTOs;

import app.restman.api.entities.security.User;
import lombok.*;

@Getter
@Setter
@Builder
public class LoginReturnDTO {
    private String token;
    private String username;
    private String role;
    private long expiresIn;
}
