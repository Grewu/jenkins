package ru.senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.annotation.Logging;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;
import ru.senla.service.api.UserService;

/**
 * The AuthenticationRestController class provides REST endpoints for user authentication and registration.
 * This controller handles requests for user registration and authentication, returning either an authorization token
 * or a user response object based on the request type. It is annotated with custom logging and validation annotations
 * and is integrated with the {@link UserService} to process authentication-related operations.
 *
 * <p>Endpoints:</p>
 * <ul>
 *     <li><b>POST /api/v0/auth/register</b>: Registers a new user and returns an authorization token.</li>
 *     <li><b>POST /api/v0/auth/authenticate</b>: Authenticates an existing user and returns user details.</li>
 * </ul>
 *
 * <p>Request format for both endpoints expects a {@link UserRequest} payload in JSON format.</p>
 *
 * @see UserRequest
 * @see UserResponse
 * @see UserService
 */
@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AuthenticationRestController.USER_API_PATH)
public class AuthenticationRestController {

    private final UserService userService;
    protected static final String USER_API_PATH = "/api/v0/auth";

    /**
     * Registers a new user in the system and returns an authorization token.
     *
     * @param userRequest the {@link UserRequest} containing user registration details.
     * @return a {@link ResponseEntity} containing the authorization token as a string
     * with HTTP status 201 (Created) and JSON media type.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getAuthorizationToken(userRequest));
    }

    /**
     * Authenticates a user and returns their details if authentication is successful.
     *
     * @param userRequest the {@link UserRequest} containing user authentication details.
     * @return a {@link ResponseEntity} containing the {@link UserResponse} with user details
     * and HTTP status 201 (Created) and JSON media type.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<UserResponse> authenticate(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.create(userRequest));
    }
}

