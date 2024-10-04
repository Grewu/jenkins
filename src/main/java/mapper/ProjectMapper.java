package mapper;

import model.dto.request.ProjectRequest;
import model.dto.response.ProjectResponse;
import model.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner",expression = "java(new UserProfile(projectRequest.owner()))")
    Project toProject(ProjectRequest projectRequest);


    @Mapping(target = "owner", source = "owner.id")
    ProjectResponse toProjectResponse(Project project);

}
