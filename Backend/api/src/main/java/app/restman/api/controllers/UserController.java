package app.restman.api.controllers;

import app.restman.api.entities.security.User;
import app.restman.api.services.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Profile("mysql")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved authenticated user",
                    content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        addSelfLink(currentUser);
        return ResponseEntity.ok(currentUser);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/")
    public ResponseEntity<CollectionModel<User>> allUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(this::addSelfLink);
        Link link = WebMvcLinkBuilder.linkTo(UserController.class).slash("/").withSelfRel();
        CollectionModel<User> userCollectionModel = CollectionModel.of(users, link);
        return ResponseEntity.ok(userCollectionModel);
    }

    // Helper method to add self link to user
    private void addSelfLink(User user) {
        Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(user.getId()).withSelfRel();
        user.add(selfLink);
    }
}
