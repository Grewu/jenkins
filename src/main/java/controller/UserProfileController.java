package controller;

import model.dto.request.UserProfileRequest;
import model.dto.response.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.api.UserProfileService;

import java.util.List;

@Validated
@RestController
@RequestMapping(value = UserProfileController.USER_PROFILE_API_PATH)
public class UserProfileController {

    private final UserProfileService userProfileService;
    public static final String USER_PROFILE_API_PATH = "/api/v0/user_profiles";

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponse> create(@RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.create(userProfileRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponse> update(@PathVariable Long id,
                                                      @RequestBody UserProfileRequest userProfileRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.update(id, userProfileRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody UserProfileRequest userProfileRequest) {
        userProfileService.delete(userProfileRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/criteria/{id}")
    public ResponseEntity<UserProfileResponse> findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.findUserProfilesWithUserDepartmentAndPositionCriteriaAPI(id));
    }

    @GetMapping("/jpql/{id}")
    public ResponseEntity<UserProfileResponse> findUserProfilesWithUserDepartmentAndPositionJPQL(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.findUserProfilesWithUserDepartmentAndPositionJPQL(id));
    }

    @GetMapping("/entitygraph/{id}")
    public ResponseEntity<UserProfileResponse> findUserProfilesWithUserDepartmentAndPositionEntityGraph(
            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userProfileService.findUserProfilesWithUserDepartmentAndPositionEntityGraph(id));
    }
}
