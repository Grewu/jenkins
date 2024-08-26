package mapper;

import model.dto.request.ProjectRequest;
import model.dto.response.ProjectResponse;
import model.entity.Project;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public Project toProject(ProjectRequest projectRequest) {
        return new Project.Builder()
                .setId(projectRequest.id())
                .setProjectName(projectRequest.projectName())
                .setDescription(projectRequest.description())
                .setStartDate(projectRequest.startDate())
                .setEndDate(projectRequest.endDate())
                .build();
    }

    public ProjectResponse toProjectResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getProjectName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate()
        );
    }

    public List<ProjectResponse> toListOfProjectResponse(List<Project> projects) {
        return projects.stream()
                .map(this::toProjectResponse)
                .collect(Collectors.toList());
    }
}
