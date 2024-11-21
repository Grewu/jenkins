package mapper;

import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignedTo", expression = "java(new UserProfile(taskRequest.assignedTo()))")
    @Mapping(target = "project", expression = "java(new Project(taskRequest.project()))")
    @Mapping(target = "createdBy", expression = "java(new UserProfile(taskRequest.createdBy()))")
    @Mapping(target = "taskHistory", ignore = true)
    Task toTask(TaskRequest taskRequest);

    @Mapping(target = "project", source = "project.id")
    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "assignedTo", source = "assignedTo.id")
    TaskResponse toTaskResponse(Task task);

}