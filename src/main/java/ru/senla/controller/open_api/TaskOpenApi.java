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
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.filter.TaskFilter;

@Tag(name = "Task", description = "API for working with tasks")
public interface TaskOpenApi {
  @Operation(
      method = "POST",
      tags = "Task",
      security = @SecurityRequirement(name = "Bearer Authentication", scopes = "task:write"),
      description = "Create a new task",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = TaskRequest.class),
                      examples =
                          @ExampleObject(
                              """
                                    {
                                      "name": "Task 1",
                                      "assignedTo": 1,
                                      "createdBy": 2,
                                      "project": 3,
                                      "dueDate": "2024-10-01T12:00:00",
                                      "status": "COMPLETED",
                                      "priority": "MEDIUM"
                                    }
                                    """))),
      responses = {
        @ApiResponse(
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "id": 5,
                                                "name": "Task 1",
                                                "project": 3,
                                                "assignedTo": 1,
                                                "createdBy": 2,
                                                "dueDate": "2024-10-01T12:00:00",
                                                "status": "COMPLETED",
                                                "priority": "MEDIUM"
                                            }
                                            """))),
        @ApiResponse(
            responseCode = "400",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "BAD_REQUEST",
                                                "message": "name = Name must not be blank"
                                            }
                                            """))),
        @ApiResponse(
            responseCode = "403",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "FORBIDDEN",
                                                "message": "Full authentication is required to access this resource"
                                            }
                                            """)))
      })
  ResponseEntity<TaskResponse> create(TaskRequest taskRequest);

  @Operation(
      method = "GET",
      tags = "Task",
      description = "Get page of task",
      parameters = {
        @Parameter(name = "page", description = "Task page", example = "0"),
        @Parameter(name = "size", description = "Page size", example = "2"),
        @Parameter(name = "sort", description = "Sorting by field", example = "id")
      },
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "name": "Design Database Schema",
                                                        "project": 1,
                                                        "assignedTo": 2,
                                                        "createdBy": 1,
                                                        "dueDate": "2024-03-01T00:00:00",
                                                        "status": "IN_PROGRESS",
                                                        "priority": "HIGH"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "name": "Develop API Endpoints",
                                                        "project": 1,
                                                        "assignedTo": 2,
                                                        "createdBy": 1,
                                                        "dueDate": "2024-06-01T00:00:00",
                                                        "status": "NOT_STARTED",
                                                        "priority": "MEDIUM"
                                                    },
                                                    {
                                                        "id": 3,
                                                        "name": "Create Marketing Plan",
                                                        "project": 2,
                                                        "assignedTo": 4,
                                                        "createdBy": 2,
                                                        "dueDate": "2024-04-01T00:00:00",
                                                        "status": "IN_PROGRESS",
                                                        "priority": "LOW"
                                                    },
                                                    {
                                                        "id": 4,
                                                        "name": "Conduct Sales Training",
                                                        "project": 2,
                                                        "assignedTo": 3,
                                                        "createdBy": 3,
                                                        "dueDate": "2024-05-01T00:00:00",
                                                        "status": "COMPLETED",
                                                        "priority": "HIGH"
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
                                            """))),
        @ApiResponse(
            responseCode = "403",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "FORBIDDEN",
                                                "message": "Full authentication is required to access this resource"
                                            }
                                            """)))
      })
  ResponseEntity<Page<TaskResponse>> getAll(@Parameter(hidden = true) Pageable pageable);

  @Operation(
      method = "GET",
      tags = "Task",
      description = "Find tasks page by filter",
      parameters = {
        @Parameter(name = "page", description = "Tasks page number", example = "0"),
        @Parameter(name = "size", description = "Number of tasks per page", example = "20"),
        @Parameter(name = "sort", description = "Field to sort by", example = "dueDate,desc"),
        @Parameter(name = "status", description = "Task status filter", example = "IN_PROGRESS"),
        @Parameter(name = "priority", description = "Task priority filter", example = "HIGH")
      },
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the tasks based on filters",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                                {
                                                    "content": [
                                                        {
                                                            "id": 1,
                                                            "name": "Design Database Schema",
                                                            "project": 1,
                                                            "assignedTo": 2,
                                                            "createdBy": 1,
                                                            "dueDate": "2024-03-01T00:00:00",
                                                            "status": "IN_PROGRESS",
                                                            "priority": "HIGH"
                                                        }
                                                    ],
                                                    "pageable": {
                                                        "pageNumber": 0,
                                                        "pageSize": 20,
                                                        "sort": {
                                                            "empty": false,
                                                            "sorted": true,
                                                            "unsorted": false
                                                        },
                                                        "offset": 0,
                                                        "paged": true,
                                                        "unpaged": false
                                                    },
                                                    "last": true,
                                                    "totalElements": 1,
                                                    "totalPages": 1,
                                                    "size": 20,
                                                    "number": 0,
                                                    "sort": {
                                                        "empty": false,
                                                        "sorted": true,
                                                        "unsorted": false
                                                    },
                                                    "first": true,
                                                    "numberOfElements": 1,
                                                    "empty": false
                                                }
                                            """))),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter parameters",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "403",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "FORBIDDEN",
                                                "message": "Full authentication is required to access this resource"
                                            }
                                            """)))
      })
  ResponseEntity<Page<TaskResponse>> getAllByFilter(
      @Parameter(hidden = true) TaskFilter taskFilter, @Parameter(hidden = true) Pageable pageable);

  @Operation(
      method = "GET",
      tags = "Task",
      description = "Get a task  by id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "id": 1,
                                                "name": "Design Database Schema",
                                                "project": 1,
                                                "assignedTo": 2,
                                                "createdBy": 1,
                                                "dueDate": "2024-03-01T00:00:00",
                                                "status": "IN_PROGRESS",
                                                "priority": "HIGH"
                                            }
                                            """))),
        @ApiResponse(
            responseCode = "404",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "NOT_FOUND",
                                                "message": "TaskHistory with ID 1 was not found"
                                            }
                                            """)))
      })
  ResponseEntity<TaskResponse> getById(@Parameter(example = "1") Long id);

  @Operation(
      method = "PUT",
      tags = "Task",
      security = @SecurityRequirement(name = "Bearer Authentication", scopes = "task:write"),
      description = "Update existed task",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = TaskRequest.class),
                      examples =
                          @ExampleObject(
                              """
                                    {
                                      "name": "Update",
                                      "assignedTo": 1,
                                      "createdBy": 2,
                                      "project": 3,
                                      "dueDate": "2024-09-30T12:00:00",
                                      "status": "IN_PROGRESS",
                                      "priority": "MEDIUM"
                                    }
                                    """))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "id": 5,
                                                "name": "Update",
                                                "project": 3,
                                                "assignedTo": 1,
                                                "createdBy": 2,
                                                "dueDate": "2024-09-30T12:00:00",
                                                "status": "IN_PROGRESS",
                                                "priority": "MEDIUM"
                                            }
                                            """))),
        @ApiResponse(
            responseCode = "403",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "FORBIDDEN",
                                                "message": "Full authentication is required to access this resource"
                                            }
                                            """))),
        @ApiResponse(
            responseCode = "404",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionMessage.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "status": "NOT_FOUND",
                                                "message": "Task with ID 1 was not found"
                                            }
                                            """)))
      })
  ResponseEntity<TaskResponse> update(@Parameter(example = "1") Long id, TaskRequest taskRequest);

  ResponseEntity<Void> delete(@Parameter(example = "1") Long id);
}
