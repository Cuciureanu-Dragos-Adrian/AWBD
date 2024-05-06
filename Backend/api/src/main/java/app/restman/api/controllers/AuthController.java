package app.restman.api.controllers;

import app.restman.api.DTOs.AuthCredentialsDTO;
import app.restman.api.DTOs.OrderReturnDTO;
import app.restman.api.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    public AuthController() { }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsDTO credentialsDTO) {
        return ResponseEntity.ok("Successfully logged in!");
    }

    @GetMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthCredentialsDTO credentialsDTO) {
        return ResponseEntity.ok("Successfully signed up!");
    }
}
