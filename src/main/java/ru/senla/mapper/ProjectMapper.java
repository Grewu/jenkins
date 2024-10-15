package ru.senla.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.entity.Project;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", expression = "java(new UserProfile(projectRequest.owner()))")
    Project toProject(ProjectRequest projectRequest);


    @Mapping(target = "owner", source = "owner.id")
    ProjectResponse toProjectResponse(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner.id", source = "owner")
    Project update(ProjectRequest projectRequest, @MappingTarget Project current);
}
