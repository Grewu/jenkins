package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.ProjectRequest;
import ru.senla.model.dto.response.ProjectResponse;
import ru.senla.model.entity.Project;

/**
 * Mapper interface for converting between {@link Project} entities and their
 * corresponding DTOs, {@link ProjectRequest} and {@link ProjectResponse}.
 *
 * <p>
 * This interface uses MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring")} to enable Spring's component
 * scanning.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {

    /**
     * Converts a {@link ProjectRequest} to a {@link Project} entity.
     *
     * @param projectRequest the DTO containing the details of the project
     * @return the converted {@link Project} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", expression = "java(new UserProfile(projectRequest.owner()))")
    Project toProject(ProjectRequest projectRequest);

    /**
     * Converts a {@link Project} entity to a {@link ProjectResponse} DTO.
     *
     * @param project the project entity to be converted
     * @return the converted {@link ProjectResponse} DTO
     */
    @Mapping(target = "owner", source = "owner.id")
    ProjectResponse toProjectResponse(Project project);

    /**
     * Updates an existing {@link Project} entity with values from a
     * {@link ProjectRequest}.
     *
     * @param projectRequest the DTO containing the updated project details
     * @param current the existing {@link Project} entity to be updated
     * @return the updated {@link Project} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner.id", source = "owner")
    Project update(ProjectRequest projectRequest, @MappingTarget Project current);
}
