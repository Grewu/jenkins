package ru.senla.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.TaskHistory;

@Mapper(componentModel = "spring")
public interface TaskHistoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", expression = "java(new Task(taskHistoryRequest.task()))")
    @Mapping(target = "assignedTo", expression = "java(new UserProfile(taskHistoryRequest.assignedTo()))")
    @Mapping(target = "project", expression = "java(new Project(taskHistoryRequest.project()))")
    @Mapping(target = "createdBy", expression = "java(new UserProfile(taskHistoryRequest.createdBy()))")
    @Mapping(target = "changedBy", expression = "java(new UserProfile(taskHistoryRequest.changedBy()))")
    TaskHistory toTaskHistory(TaskHistoryRequest taskHistoryRequest);

    @Mapping(target = "task", source = "task.id")
    @Mapping(target = "project", source = "project.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "assignedTo", source = "assignedTo.id")
    @Mapping(target = "changedBy", source = "changedBy.id")
    TaskHistoryResponse toTaskHistoryResponse(TaskHistory taskHistory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task.id", source = "task")
    @Mapping(target = "project.id", source = "project")
    @Mapping(target = "createdBy.id", source = "createdBy")
    @Mapping(target = "assignedTo.id", source = "assignedTo")
    @Mapping(target = "changedBy.id", source = "changedBy")
    TaskHistory update(TaskHistoryRequest taskHistoryRequest, @MappingTarget TaskHistory current);
}
