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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import ru.senla.exception.ExceptionMessage;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.DepartmentResponse;
import ru.senla.model.dto.response.ProjectResponse;

@Tag(name = "Project", description = "API for working with projects")
public interface ProjectOpenApi {
    @Operation(
            method = "POST",
            tags = "Department",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "department:write"),
            description = "Create a new comment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectRequest.class),
                            examples = @ExampleObject("""
                                    {
                                        "name": "New Project",
                                        "projectCode": "NP-001",
                                        "description": "This is a new project.",
                                        "startDate": "2024-09-27T10:00:00",
                                        "endDate": "2024-12-27T10:00:00",
                                        "owner": 1
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DepartmentResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 4,
                                                "name": "New Project",
                                                "projectCode": "NP-001",
                                                "description": "This is a new project.",
                                                "startDate": "2024-09-27T10:00:00",
                                                "endDate": "2024-12-27T10:00:00",
                                                "owner": 1
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
                                                "message": "name = Project name must not be blank"
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
    ResponseEntity<ProjectResponse> create(ProjectRequest projectRequest);

    @Operation(
            method = "GET",
            tags = "Project",
            description = "Get page of projects",
            parameters = {
                    @Parameter(name = "page", description = "Projects page", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "2"),
                    @Parameter(name = "sort", description = "Sorting by field", example = "id")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProjectResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "name": "Web Application Development",
                                                        "projectCode": "PROJ001",
                                                        "description": "A project focused on developing a multi-functional web application for task management.",
                                                        "startDate": "2024-09-01T00:00:00",
                                                        "endDate": "2024-12-31T00:00:00",
                                                        "owner": 1
                                                    },
                                                    {
                                                        "id": 2,
                                                        "name": "Database Migration",
                                                        "projectCode": "PROJ002",
                                                        "description": "A project aimed at migrating the existing database to a new platform.",
                                                        "startDate": "2024-10-01T00:00:00",
                                                        "endDate": "2025-01-15T00:00:00",
                                                        "owner": 2
                                                    },
                                                    {
                                                        "id": 3,
                                                        "name": "Security System Upgrade",
                                                        "projectCode": "PROJ003",
                                                        "description": "A project to implement new security measures in the existing system.",
                                                        "startDate": "2024-11-01T00:00:00",
                                                        "endDate": "2025-02-28T00:00:00",
                                                        "owner": 3
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
                                                "totalElements": 3,
                                                "totalPages": 1,
                                                "size": 20,
                                                "number": 0,
                                                "first": true,
                                                "numberOfElements": 3,
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
    ResponseEntity<Page<ProjectResponse>> getAll(@Parameter(hidden = true) Pageable pageable);


    @Operation(
            method = "GET",
            tags = "Project",
            description = "Get a project by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProjectResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "name": "Web Application Development",
                                                "projectCode": "PROJ001",
                                                "description": "A project focused on developing a multi-functional web application for task management.",
                                                "startDate": "2024-09-01T00:00:00",
                                                "endDate": "2024-12-31T00:00:00",
                                                "owner": 1
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
                                                "message": "Project with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<ProjectResponse> getById(@Parameter(example = "1") Long id);

    @Operation(
            method = "PUT",
            tags = "Project",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "project:write"),
            description = "Update existed comment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProjectRequest.class),
                            examples = @ExampleObject("""
                                    {
                                      "name": "Update Project",
                                      "projectCode": "NP-001",
                                      "description": "This is a new project.",
                                      "startDate": "2024-09-27T10:00:00",
                                      "endDate": "2024-12-27T10:00:00",
                                      "owner": 1
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProjectResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "name": "Update Project",
                                                "projectCode": "NP-001",
                                                "description": "This is a new project.",
                                                "startDate": "2024-09-27T10:00:00",
                                                "endDate": "2024-12-27T10:00:00",
                                                "owner": 1
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
                                                "message": "Project with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<ProjectResponse> update(@Parameter(example = "1") Long id, ProjectRequest projectRequest);

    @Operation(
            method = "DELETE",
            tags = "Department",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "comments:delete"),
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
