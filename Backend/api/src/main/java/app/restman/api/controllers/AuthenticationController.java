package app.restman.api.controllers;


import app.restman.api.DTOs.LoginUserDto;
import app.restman.api.DTOs.RegisterUserDto;
import app.restman.api.entities.security.User;
import app.restman.api.DTOs.LoginReturnDTO;
import app.restman.api.services.OrderService;
import app.restman.api.services.security.AuthenticationService;
import app.restman.api.services.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RequestMapping("/auth")
@RestController
@Profile("mysql")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Create a 'guest' user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "User could not be created",
                    content = @Content)})
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signupRegularUser(registerUserDto);
            return ResponseEntity.ok("User created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Create a 'staff' user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "User could not be created",
                    content = @Content)})
    @PostMapping("/signupStaff")
    public ResponseEntity<?> registerStaff(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signupStaff(registerUserDto);
            return ResponseEntity.ok("Staff created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginReturnDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Login failed",
                    content = @Content)})
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginReturnDTO loginResponse = LoginReturnDTO.builder()
                    .token(jwtToken)
                    .username(authenticatedUser.getFullName())
                    .role(authenticatedUser.getRole().toString())
                    .expiresIn(jwtService.getExpirationTime())
                    .build();

            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}