package controller;

import lombok.RequiredArgsConstructor;
import model.dto.request.UserRequest;
import model.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.api.UserService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = AuthenticationController.USER_API_PATH)
public class AuthenticationController {

    private final UserService userService;

    public static final String USER_API_PATH = "/api/v0/auth";

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getAuthorizationToken(userRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserResponse> authenticate(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.create(userRequest));
    }
}
