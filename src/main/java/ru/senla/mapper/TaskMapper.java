package ru.senla.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.senla.model.dto.request.TaskRequest;
import ru.senla.model.dto.response.TaskResponse;
import ru.senla.model.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedTo", expression = "java(new UserProfile(taskRequest.assignedTo()))")
    @Mapping(target = "project", expression = "java(new Project(taskRequest.project()))")
    @Mapping(target = "createdBy", expression = "java(new UserProfile(taskRequest.createdBy()))")
    Task toTask(TaskRequest taskRequest);

    @Mapping(target = "project", source = "project.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "assignedTo", source = "assignedTo.id")
    TaskResponse toTaskResponse(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project.id", source = "project")
    @Mapping(target = "createdBy.id", source = "createdBy")
    @Mapping(target = "assignedTo.id", source = "assignedTo")
    Task update(TaskRequest taskRequest, @MappingTarget Task current);
}