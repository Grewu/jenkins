package ru.senla.controller.open_api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.senla.exception.ExceptionMessage;
import ru.senla.model.dto.request.CommentRequest;
import ru.senla.model.dto.response.CommentResponse;

@Tag(name = "Comment", description = "API for working with comments")
public interface CommentOpenApi {
    @Operation(
            method = "POST",
            tags = "Comment",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "comments:write"),
            description = "Create a new comment",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentRequest.class),
                            examples = @ExampleObject("""
                                    {
                                      "task": 1,
                                      "usersProfile": 2,
                                      "commentText": "commentTest",
                                      "createdAt": "2024-09-27T12:34:56"
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "task": 1,
                                                "usersProfile": 2,
                                                "commentText": "commentTest",
                                                "createdAt": "2024-09-27T12:34:56"
                                            }
                                            """)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionMessage.class),
                                    examples = @ExampleObject("""
                                            {
                                                "status": "BAD_REQUEST",
                                                "message": "commentText = Comment text must not be null"
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
    ResponseEntity<CommentResponse> create(CommentRequest commentRequest);


    @Operation(
            method = "GET",
            tags = "Comment",
            description = "Get page of comments",
            parameters = {
                    @Parameter(name = "page", description = "Comments page", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "2"),
                    @Parameter(name = "sort", description = "Sorting by field", example = "id")
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentResponse.class),
                            examples = @ExampleObject("""
                                    {
                                        "content": [
                                            {
                                                "id": 1,
                                                "task": 1,
                                                "usersProfile": 2,
                                                "commentText": "Initial schema design completed.",
                                                "createdAt": "2024-01-15T10:00:00"
                                            },
                                            {
                                                "id": 2,
                                                "task": 1,
                                                "usersProfile": 2,
                                                "commentText": "Reviewed schema with the team.",
                                                "createdAt": "2024-01-20T14:00:00"
                                            },
                                            {
                                                "id": 3,
                                                "task": 2,
                                                "usersProfile": 2,
                                                "commentText": "Started working on API endpoints.",
                                                "createdAt": "2024-03-01T09:00:00"
                                            },
                                            {
                                                "id": 4,
                                                "task": 3,
                                                "usersProfile": 4,
                                                "commentText": "Draft marketing plan ready for review.",
                                                "createdAt": "2024-02-15T11:30:00"
                                            }
                                        ],
                                        "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 20,
                                            "sort": {
                                                "empty": true,
                                                "sorted": false,
                                                "unsorted": true
                                            },
                                            "offset": 0,
                                            "paged": true,
                                            "unpaged": false
                                        },
                                        "last": true,
                                        "totalElements": 4,
                                        "totalPages": 1,
                                        "size": 20,
                                        "number": 0,
                                        "sort": {
                                            "empty": true,
                                            "sorted": false,
                                            "unsorted": true
                                        },
                                        "first": true,
                                        "numberOfElements": 4,
                                        "empty": false
                                    }
                                    """)
                    )
            )
    )
    ResponseEntity<Page<CommentResponse>> getAll(@Parameter(hidden = true) Pageable pageable);


    @Operation(
            method = "GET",
            tags = "Comment",
            description = "Get a comment by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "task": 1,
                                                "usersProfile": 2,
                                                "commentText": "Initial schema design completed.",
                                                "createdAt": "2024-01-15T10:00:00"
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
                                                "message": "Comment with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<CommentResponse> getById(@Parameter(example = "1") Long id);


    @Operation(
            method = "PUT",
            tags = "Comment",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "comments:write"),
            description = "Update existed comment",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentRequest.class),
                            examples = @ExampleObject("""
                                    {
                                      "task": 1,
                                      "usersProfile": 2,
                                      "commentText": "commentText",
                                      "createdAt": "2024-09-27T12:34:56"
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "task": 1,
                                                "usersProfile": 2,
                                                "commentText": "commentText",
                                                "createdAt": "2024-09-27T12:34:56"
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
                                                "message": "Comment with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<CommentResponse> update(@Parameter(example = "1") Long id, CommentRequest commentRequest);

    @Operation(
            method = "DELETE",
            tags = "Comment",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "department:delete"),
            description = "Delete a comment by id",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully processed, no content to return",
                            content = @Content
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
    ResponseEntity<Void> delete(@Parameter(example = "1") Long id);
}
