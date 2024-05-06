package app.restman.api.controllers;

import app.restman.api.DTOs.AuthCredentialsDTO;
import app.restman.api.DTOs.OrderReturnDTO;
import app.restman.api.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    public AuthController() { }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsDTO credentialsDTO) {
        return ResponseEntity.ok("Successfully logged in!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthCredentialsDTO credentialsDTO) {
        return ResponseEntity.ok("Successfully signed up!");
    }
}
