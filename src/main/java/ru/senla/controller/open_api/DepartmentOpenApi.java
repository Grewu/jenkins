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
import ru.senla.model.dto.request.DepartmentRequest;
import ru.senla.model.dto.response.DepartmentResponse;

@Tag(name = "Department", description = "API for working with departments")
public interface DepartmentOpenApi {
  @Operation(
      method = "POST",
      tags = "Department",
      security = @SecurityRequirement(name = "Bearer Authentication", scopes = "department:write"),
      description = "Create a new comment",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = DepartmentRequest.class),
                      examples =
                          @ExampleObject(
                              """
                                    {
                                      "name": "SALES"
                                    }
                                    """))),
      responses = {
        @ApiResponse(
            responseCode = "201",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "id": 1,
                                                "name": "SALES"
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
                                                "message": "name = Department name cannot be null"
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
  ResponseEntity<DepartmentResponse> create(DepartmentRequest departmentRequest);

  @Operation(
      method = "GET",
      tags = "Department",
      description = "Get page of departments",
      parameters = {
        @Parameter(name = "page", description = "Departments page", example = "0"),
        @Parameter(name = "size", description = "Page size", example = "2"),
        @Parameter(name = "sort", description = "Sorting by field", example = "id")
      },
      responses =
          @ApiResponse(
              responseCode = "200",
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = DepartmentResponse.class),
                      examples =
                          @ExampleObject(
                              """
                                    {
                                        "content": [
                                            {
                                                "id": 1,
                                                "name": "DEVELOPERS"
                                            },
                                            {
                                                "id": 2,
                                                "name": "MANAGERS"
                                            },
                                            {
                                                "id": 3,
                                                "name": "MARKETING"
                                            },
                                            {
                                                "id": 4,
                                                "name": "HR"
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
                                        "numberOfElements": 4,
                                        "first": true,
                                        "empty": false
                                    }
                                    """))))
  ResponseEntity<Page<DepartmentResponse>> getAll(@Parameter(hidden = true) Pageable pageable);

  @Operation(
      method = "GET",
      tags = "Department",
      description = "Get a department by id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "id": 1,
                                                "name": "DEVELOPERS"
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
                                                "message": "Department with ID 1 was not found"
                                            }
                                            """)))
      })
  ResponseEntity<DepartmentResponse> getById(@Parameter(example = "1") Long id);

  @Operation(
      method = "PUT",
      tags = "Department",
      security = @SecurityRequirement(name = "Bearer Authentication", scopes = "department:write"),
      description = "Update existed comment",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = DepartmentRequest.class),
                      examples =
                          @ExampleObject(
                              """
                                    {
                                      "name": "ADMINISTRATION"
                                    }
                                    """))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class),
                    examples =
                        @ExampleObject(
                            """
                                            {
                                                "id": 1,
                                                "name": "ADMINISTRATION"
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
                                                "message": "Department with ID 1 was not found"
                                            }
                                            """)))
      })
  ResponseEntity<DepartmentResponse> update(
      @Parameter(example = "1") Long id, DepartmentRequest departmentRequest);

  @Operation(
      method = "DELETE",
      tags = "Department",
      security = @SecurityRequirement(name = "Bearer Authentication", scopes = "comments:delete"),
      description = "Delete a comment by id",
      responses = {
        @ApiResponse(
            responseCode = "204",
            description = "Successfully processed, no content to return",
            content = @Content),
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
      })
  ResponseEntity<Void> delete(@Parameter(example = "1") Long id);
}
