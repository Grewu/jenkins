package ru.senla.controller.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.senla.exception.ExceptionMessage;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.dto.response.UserProfileResponse;

@Tag(name = "UserProfile", description = "API for working with user profiles")
public interface UserProfileOpenApi {
    @Operation(
            method = "POST",
            tags = "UserProfile",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "user_profile:write"),
            description = "Create a new user profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserProfileResponse.class),
                            examples = @ExampleObject("""
                                    {
                                      "firstName": "Ivan",
                                      "lastName": "Ivanov",
                                      "position": 1,
                                      "department": 2,
                                      "user": 1
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserProfileResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 5,
                                                "firstName": "Ivan",
                                                "lastName": "Ivanov",
                                                "position": 1,
                                                "department": 2,
                                                "user": 1
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
                    )
            }
    )
    ResponseEntity<UserProfileResponse> create(UserProfileRequest userProfileRequest);

    @Operation(
            method = "GET",
            tags = "UserProfile",
            description = "Get page of user profiles",
            parameters = {
                    @Parameter(name = "page", description = "UserProfile page", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "2"),
                    @Parameter(name = "sort", description = "Sorting by field", example = "id")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserProfileResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "firstName": "Alice",
                                                        "lastName": "Smith",
                                                        "position": 2,
                                                        "department": 1,
                                                        "user": 1
                                                    },
                                                    {
                                                        "id": 2,
                                                        "firstName": "Bob",
                                                        "lastName": "Johnson",
                                                        "position": 1,
                                                        "department": 2,
                                                        "user": 2
                                                    },
                                                    {
                                                        "id": 3,
                                                        "firstName": "Charlie",
                                                        "lastName": "Williams",
                                                        "position": 3,
                                                        "department": 3,
                                                        "user": 3
                                                    },
                                                    {
                                                        "id": 4,
                                                        "firstName": "David",
                                                        "lastName": "Brown",
                                                        "position": 4,
                                                        "department": 4,
                                                        "user": 4
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 20,
                                                    "sort": {
                                                        "unsorted": true,
                                                        "empty": true,
                                                        "sorted": false
                                                    },
                                                    "offset": 0,
                                                    "paged": true,
                                                    "unpaged": false
                                                },
                                                "last": true,
                                                "totalElements": 4,
                                                "totalPages": 1,
                                                "first": true,
                                                "numberOfElements": 4,
                                                "size": 20,
                                                "number": 0,
                                                "sort": {
                                                    "unsorted": true,
                                                    "empty": true,
                                                    "sorted": false
                                                },
                                                "empty": false
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
                    )
            }
    )
    ResponseEntity<Page<UserProfileResponse>> getAll(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            method = "GET",
            tags = "UserProfile",
            description = "Get a user profiles by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserProfileResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "firstName": "Alice",
                                                "lastName": "Smith",
                                                "position": 2,
                                                "department": 1,
                                                "user": 1
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class),
                                    examples = @ExampleObject("""
                                            {
                                                "status": "NOT_FOUND",
                                                "message": "UserProfile with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<UserProfileResponse> getById(@Parameter(example = "1") Long id);

    @Operation(
            method = "PUT",
            tags = "UserProfile",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "user_profile:write"),
            description = "Update existed user profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskRequest.class),
                            examples = @ExampleObject("""
                                    {
                                      "firstName": "Pavel",
                                      "lastName": "Pavlovich",
                                      "department": 1,
                                      "position": 2,
                                      "user": 1
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "firstName": "Pavel",
                                                "lastName": "Pavlovich",
                                                "position": 2,
                                                "department": 1,
                                                "user": 1
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
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class),
                                    examples = @ExampleObject("""
                                            {
                                                "status": "NOT_FOUND",
                                                "message": "UserProfile with ID 11234 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<UserProfileResponse> update(@Parameter(example = "1") Long id, UserProfileRequest userProfileRequest);

    ResponseEntity<Void> delete(@Parameter(example = "1") Long id);
}
