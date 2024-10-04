package data;

import lombok.Builder;
import model.dto.request.ProjectRequest;
import model.dto.response.ProjectResponse;
import model.entity.Project;
import model.entity.UserProfile;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder(setterPrefix = "with")
public class ProjectTestData {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "name";

    @Builder.Default
    private String projectCode = "projectCode";

    @Builder.Default
    private String description = "description";

    @Builder.Default
    private LocalDateTime startDate = LocalDateTime.parse("2024-09-30T12:00:00");

    @Builder.Default
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate = LocalDateTime.parse("2024-09-30T12:00:00");

    @Builder.Default
    private UserProfile owner = UserProfileTestData.builder().build().buildUserProfile();

    public Project buildProject() {
        return new Project(id, name, projectCode, description, startDate, endDate, owner);
    }

    public ProjectRequest buildProjectRequest() {
        return new ProjectRequest(name, projectCode, description, startDate, endDate, owner.getId());
    }

    public ProjectResponse buildProjectResponse() {
        return new ProjectResponse(id, name, projectCode, description, startDate, endDate, owner.getId());
    }
}
