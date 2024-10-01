package mapper;

import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import model.entity.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public Task toTask(TaskRequest taskRequest) {
        return new Task.Builder()
                .setId(taskRequest.id())
                .setTaskName(taskRequest.taskName())
                .setProjectId(taskRequest.projectId())
                .setAssignedTo(taskRequest.assignedTo())
                .setDueDate(taskRequest.dueDate())
                .setStatus(taskRequest.status())
                .setPriority(taskRequest.priority())
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTaskName(),
                task.getAssignedTo(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority()
        );
    }

    public List<TaskResponse> toListOfTaskResponse(List<Task> tasks) {
        return tasks.stream()
                .map(this::toTaskResponse)
                .collect(Collectors.toList());
    }
}
