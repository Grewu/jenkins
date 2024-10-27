package ru.senla.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.annotation.Logging;
import ru.senla.model.dto.request.UserProfileRequest;
import ru.senla.model.dto.response.CommentResponse;
import ru.senla.model.dto.response.UserProfileResponse;
import ru.senla.service.api.UserProfileService;

@Logging
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserProfileRestController.USER_PROFILE_API_PATH)
public class UserProfileRestController {

    protected static final String USER_PROFILE_API_PATH = "/api/v0/user_profiles";
    private final UserProfileService userProfileService;


    @PostMapping
    @PreAuthorize("hasAuthority('user_profile:write')")
    public ResponseEntity<UserProfileResponse> create(@RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.create(userProfileRequest));
    }

    @GetMapping("/{userProfileId}/comments")
    @PreAuthorize("hasAuthority('user_profile:read') && hasAuthority('comments:read')")
    public ResponseEntity<Page<CommentResponse>> getCommentsByProfileId(@PathVariable Long userProfileId,
                                                                        @PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getAllCommentsByProfileId(userProfileId, pageable));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user_profile:read')")
    public ResponseEntity<Page<UserProfileResponse>> getAll(@PageableDefault(20) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user_profile:read')")
    public ResponseEntity<UserProfileResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user_profile:write')")
    public ResponseEntity<UserProfileResponse> update(@PathVariable Long id,
                                                      @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.update(id, userProfileRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user_profile:delete')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
