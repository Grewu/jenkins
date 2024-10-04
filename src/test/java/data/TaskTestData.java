package data;

import lombok.Builder;
import model.dto.request.TaskRequest;
import model.dto.response.TaskResponse;
import model.entity.Project;
import model.entity.Task;
import model.entity.UserProfile;
import model.entity.enums.PriorityType;
import model.entity.enums.StatusType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Builder(setterPrefix = "with")
public class TaskTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "name";

    @Builder.Default
    private Project project = ProjectTestData.builder().build().buildProject();

    @Builder.Default
    private UserProfile assignedTo = UserProfileTestData.builder().build().buildUserProfile();

    @Builder.Default
    private UserProfile createdBy = UserProfileTestData.builder().build().buildUserProfile();

    @Builder.Default
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dueDate = LocalDateTime.parse("2024-09-30T12:00:00");

    @Builder.Default
    private StatusType status = StatusType.IN_PROGRESS;

    @Builder.Default
    private PriorityType priority = PriorityType.MEDIUM;

    public Task buildTask() {
        return new Task(id, name, project, assignedTo, createdBy, dueDate, status, priority, null);
    }

    public TaskRequest buildTaskRequest() {
        return new TaskRequest(name, project.getId(), assignedTo.getId(), createdBy.getId(), dueDate, status, priority);
    }

    public TaskResponse buildTaskResponse() {
        return new TaskResponse(id, name, project.getId(), assignedTo.getId(), createdBy.getId(), dueDate, status, priority);
    }
}
