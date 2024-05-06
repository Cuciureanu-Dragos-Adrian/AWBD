package app.restman.api.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredentialsDTO {

    private String username;
    private String password;

    AuthCredentialsDTO() { }
}
