package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.entity.Task;

/**
 * Mapper interface for converting between {@link Task} entities and their corresponding DTOs,
 * {@link TaskRequest} and {@link TaskResponse}.
 *
 * <p>This interface utilizes MapStruct to generate the implementation for mapping properties
 * between the entities and DTOs. It is annotated with {@code @Mapper(componentModel = "spring")} to
 * enable Spring's component scanning.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {

  /**
   * Converts a {@link TaskRequest} to a {@link Task} entity.
   *
   * @param taskRequest the DTO containing the details of the task
   * @return the converted {@link Task} entity
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "assignedTo", expression = "java(new UserProfile(taskRequest.assignedTo()))")
  @Mapping(target = "project", expression = "java(new Project(taskRequest.project()))")
  @Mapping(target = "createdBy", expression = "java(new UserProfile(taskRequest.createdBy()))")
  Task toTask(TaskRequest taskRequest);

  /**
   * Converts a {@link Task} entity to a {@link TaskResponse} DTO.
   *
   * @param task the task entity to be converted
   * @return the converted {@link TaskResponse} DTO
   */
  @Mapping(target = "project", source = "project.id")
  @Mapping(target = "createdBy", source = "createdBy.id")
  @Mapping(target = "assignedTo", source = "assignedTo.id")
  TaskResponse toTaskResponse(Task task);

  /**
   * Updates an existing {@link Task} entity with values from a {@link TaskRequest}.
   *
   * @param taskRequest the DTO containing the updated task details
   * @param current the existing {@link Task} entity to be updated
   * @return the updated {@link Task} entity
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "project.id", source = "project")
  @Mapping(target = "createdBy.id", source = "createdBy")
  @Mapping(target = "assignedTo.id", source = "assignedTo")
  Task update(TaskRequest taskRequest, @MappingTarget Task current);
}
