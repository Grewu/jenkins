package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.TaskHistory;

/**
 * Mapper interface for converting between {@link TaskHistory} entities and their
 * corresponding DTOs, {@link TaskHistoryRequest} and {@link TaskHistoryResponse}.
 *
 * <p>
 * This interface uses MapStruct to generate the implementation for mapping
 * properties between the entities and DTOs. It is annotated with
 * {@code @Mapper(componentModel = "spring")} to enable Spring's component
 * scanning.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {

    /**
     * Converts a {@link TaskHistoryRequest} to a {@link TaskHistory} entity.
     *
     * @param taskHistoryRequest the DTO containing the details of the task history
     * @return the converted {@link TaskHistory} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", expression = "java(new Task(taskHistoryRequest.task()))")
    @Mapping(target = "assignedTo", expression = "java(new UserProfile(taskHistoryRequest.assignedTo()))")
    @Mapping(target = "project", expression = "java(new Project(taskHistoryRequest.project()))")
    @Mapping(target = "createdBy", expression = "java(new UserProfile(taskHistoryRequest.createdBy()))")
    @Mapping(target = "changedBy", expression = "java(new UserProfile(taskHistoryRequest.changedBy()))")
    TaskHistory toTaskHistory(TaskHistoryRequest taskHistoryRequest);

    /**
     * Converts a {@link TaskHistory} entity to a {@link TaskHistoryResponse} DTO.
     *
     * @param taskHistory the task history entity to be converted
     * @return the converted {@link TaskHistoryResponse} DTO
     */
    @Mapping(target = "task", source = "task.id")
    @Mapping(target = "project", source = "project.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "assignedTo", source = "assignedTo.id")
    @Mapping(target = "changedBy", source = "changedBy.id")
    TaskHistoryResponse toTaskHistoryResponse(TaskHistory taskHistory);

    /**
     * Updates an existing {@link TaskHistory} entity with values from a
     * {@link TaskHistoryRequest}.
     *
     * @param taskHistoryRequest the DTO containing the updated task history details
     * @param current            the existing {@link TaskHistory} entity to be updated
     * @return the updated {@link TaskHistory} entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task.id", source = "task")
    @Mapping(target = "project.id", source = "project")
    @Mapping(target = "createdBy.id", source = "createdBy")
    @Mapping(target = "assignedTo.id", source = "assignedTo")
    @Mapping(target = "changedBy.id", source = "changedBy")
    TaskHistory update(TaskHistoryRequest taskHistoryRequest, @MappingTarget TaskHistory current);
}
