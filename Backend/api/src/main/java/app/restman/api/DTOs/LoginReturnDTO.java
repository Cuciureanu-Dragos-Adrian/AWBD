package app.restman.api.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginReturnDTO {
    private String token;
    private String username;
    private long expiresIn;
}
