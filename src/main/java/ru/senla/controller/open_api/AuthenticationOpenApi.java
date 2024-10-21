package ru.senla.controller.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.senla.exception.ExceptionMessage;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;

@Tag(name = " Authentication", description = "API for working with users")
public interface AuthenticationOpenApi {
    @Operation(
            method = "POST",
            tags = "User",
            description = "Get JWT token user by login and password",
            parameters = {
                    @Parameter(name = "email", example = "admin@example.com", required = true),
                    @Parameter(name = "password", example = "securepassword123", required = true),
                    @Parameter(name = "role", example = "1", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlz
                                            cyI6IlNlbmxhIiwiYXV0aG9yaXRpZXMiOlsiY29tbWVudHM6cmVhZCIsImNvbW1
                                            lbnRzOndyaXRlIiwiY29tbWVudHM6ZGVsZXRlIiwidGFzazpyZWFkIiwidGFzazp3cml0Z
                                            SIsInRhc2s6ZGVsZXRlIiwidGFza19oaXN0b3J5OnJlYWQiLCJ0YXNrX2hpc3Rvcnk6d3JpdGUi
                                            LCJ0YXNrX2hpc3Rvcnk6ZGVsZXRlIiwidXNlcl9wcm9maWxlOnJlYWQiLCJ1c2VyX3Byb2ZpbGU
                                            6d3JpdGUiLCJ1c2VyX3Byb2ZpbGU6ZGVsZXRlIiwidXNlcjpyZWFkIiwidXNlcjp3cml0ZSIsIn
                                            VzZXI6ZGVsZXRlIiwicHJvamVjdDpyZWFkIiwicHJvamVjdDp3cml0ZSIsInByb2plY3Q6ZGVsZX
                                            RlIiwiZGVwYXJ0bWVudDpyZWFkIiwiZGVwYXJ0bWVudDp3cml0ZSIsImRlcGFydG1lbnQ6ZGVsZX
                                            RlIl0sImlhdCI6MTcy
                                            OTE3MDM4OCwiZXhwIjoxNzI5Nzc1MTg4fQ.PWl-TXiEtFaid_avGHgRsl6RzxvU2DcLtRrMaKoeX
                                            RREIaVtCptpMebWK0odGATglg6pf55AaXwgshxEKU775w
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class),
                                    examples = @ExampleObject("""
                                            {
                                                "status": "UNAUTHORIZED",
                                                "message": "Invalid email: admin@example.c1om"
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class),
                                    examples = @ExampleObject("""
                                            {
                                                "status": "FORBIDDEN",
                                                "message": "Full authentication is required to access this resource"
                                            }
                                            """)
                            )
                    ),
            }
    )
    ResponseEntity<String> register(UserRequest userRequest);

    @Operation(
            method = "POST",
            tags = "User",
            description = "Create new user ",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "id": 5,
                                                "email": "test@example.com",
                                                "role": 3
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject("""
                                            {
                                                "status": "UNAUTHORIZED",
                                                "message": "Invalid email: test@example.com1"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> authenticate(@Parameter(hidden = true) UserRequest userRequest);


}
