package app.restman.api.responses;

import lombok.*;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;

}
