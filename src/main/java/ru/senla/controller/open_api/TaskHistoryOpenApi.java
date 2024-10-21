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
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.dto.response.TaskResponse;

@Tag(name = "TaskHistory", description = "API for working with task history")
public interface TaskHistoryOpenApi {
    @Operation(
            method = "GET",
            tags = "TaskHistory",
            description = "Get page of task history",
            parameters = {
                    @Parameter(name = "page", description = "TaskHistory page", example = "0"),
                    @Parameter(name = "size", description = "Page size", example = "2"),
                    @Parameter(name = "sort", description = "Sorting by field", example = "id")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "task": 1,
                                                        "name": "Design Login Page",
                                                        "project": 1,
                                                        "assignedTo": 2,
                                                        "createdBy": 1,
                                                        "dueDate": "2024-09-15T00:00:00",
                                                        "status": "IN_PROGRESS",
                                                        "priority": "HIGH",
                                                        "changedBy": 2,
                                                        "changedDate": "2024-09-12T00:00:00",
                                                        "changedDescription": "Initial task creation and assignment."
                                                    },
                                                    {
                                                        "id": 2,
                                                        "task": 2,
                                                        "name": "Database Schema Update",
                                                        "project": 2,
                                                        "assignedTo": 3,
                                                        "createdBy": 2,
                                                        "dueDate": "2024-10-20T00:00:00",
                                                        "status": "NOT_STARTED",
                                                        "priority": "MEDIUM",
                                                        "changedBy": 3,
                                                        "changedDate": "2024-10-01T00:00:00",
                                                        "changedDescription": "Task created for database migration project."
                                                    },
                                                    {
                                                        "id": 3,
                                                        "task": 3,
                                                        "name": "Security Audit",
                                                        "project": 3,
                                                        "assignedTo": 4,
                                                        "createdBy": 3,
                                                        "dueDate": "2024-11-25T00:00:00",
                                                        "status": "COMPLETED",
                                                        "priority": "LOW",
                                                        "changedBy": 4,
                                                        "changedDate": "2024-11-10T00:00:00",
                                                        "changedDescription": "Security audit scheduled to enhance system security."
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
                                                "sort": {
                                                    "empty": true,
                                                    "sorted": false,
                                                    "unsorted": true
                                                },
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
    ResponseEntity<Page<TaskHistoryResponse>> getAll(@Parameter(hidden = true) Pageable pageable);

    @Operation(
            method = "GET",
            tags = "TaskHistory",
            description = "Get a task history by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskHistoryResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "task": 1,
                                                "name": "Design Login Page",
                                                "project": 1,
                                                "assignedTo": 2,
                                                "createdBy": 1,
                                                "dueDate": "2024-09-15T00:00:00",
                                                "status": "IN_PROGRESS",
                                                "priority": "HIGH",
                                                "changedBy": 2,
                                                "changedDate": "2024-09-12T00:00:00",
                                                "changedDescription": "Initial task creation and assignment."
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
                                                "message": "TaskHistory with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<TaskHistoryResponse> getById(@Parameter(example = "1") Long id);

    @Operation(
            method = "PUT",
            tags = "TaskHistory",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "task_history:write"),
            description = "Update existed task history",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskHistoryRequest.class),
                            examples = @ExampleObject("""
                                    {
                                        "id": 1,
                                        "task": 1,
                                        "name": "Design New Login Page",
                                        "project": 1,
                                        "assignedTo": 2,
                                        "createdBy": 1,
                                        "dueDate": "2024-09-15T00:00:00",
                                        "status": "IN_PROGRESS",
                                        "priority": "HIGH",
                                        "changedBy": 2,
                                        "changedDate": "2024-09-12T00:00:00",
                                        "changedDescription": "Initial task creation and assignment."
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TaskHistoryResponse.class),
                                    examples = @ExampleObject("""
                                            {
                                                "id": 1,
                                                "task": 1,
                                                "name": "Design New Login Page",
                                                "project": 1,
                                                "assignedTo": 2,
                                                "createdBy": 1,
                                                "dueDate": "2024-09-15T00:00:00",
                                                "status": "IN_PROGRESS",
                                                "priority": "HIGH",
                                                "changedBy": 2,
                                                "changedDate": "2024-09-12T00:00:00",
                                                "changedDescription": "Initial task creation and assignment."
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
                                                "message": "TaskHistory with ID 1 was not found"
                                            }
                                            """)
                            )
                    )
            }
    )
    ResponseEntity<TaskHistoryResponse> update(@Parameter(example = "1") Long id, TaskHistoryRequest taskHistoryRequest);

    @Operation(
            method = "DELETE",
            tags = "Department",
            security = @SecurityRequirement(name = "Bearer Authentication", scopes = "task_history:delete"),
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
