package ru.senla.data;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import ru.senla.model.dto.request.TaskHistoryRequest;
import ru.senla.model.dto.response.TaskHistoryResponse;
import ru.senla.model.entity.Project;
import ru.senla.model.entity.Task;
import ru.senla.model.entity.TaskHistory;
import ru.senla.model.entity.UserProfile;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;

@Builder(setterPrefix = "with")
public class TaskHistoryTestData {

  @Builder.Default private Long id = 1L;

  @Builder.Default private Task task = TaskTestData.builder().build().buildTask();

  @Builder.Default private String name = "name";

  @Builder.Default private Project project = ProjectTestData.builder().build().buildProject();

  @Builder.Default
  private UserProfile assignedTo = UserProfileTestData.builder().build().buildUserProfile();

  @Builder.Default
  private UserProfile createdBy = UserProfileTestData.builder().build().buildUserProfile();

  @Builder.Default
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime dueDate = LocalDateTime.parse("2024-09-30T12:00:00");

  @Builder.Default private StatusType status = StatusType.IN_PROGRESS;

  @Builder.Default private PriorityType priority = PriorityType.MEDIUM;

  @Builder.Default
  private UserProfile changedBy = UserProfileTestData.builder().build().buildUserProfile();

  @Builder.Default
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime changedDate = LocalDateTime.parse("2024-09-30T12:00:00");

  @Builder.Default private String changedDescription = "changedDescription";

  public TaskHistory buildTaskHistory() {
    return new TaskHistory(
        id,
        task,
        name,
        project,
        assignedTo,
        createdBy,
        dueDate,
        status,
        priority,
        changedBy,
        changedDate,
        changedDescription);
  }

  public TaskHistoryRequest buildTaskHistoryRequest() {
    return new TaskHistoryRequest(
        task.getId(),
        name,
        project.getId(),
        assignedTo.getId(),
        createdBy.getId(),
        dueDate,
        status,
        priority,
        changedBy.getId(),
        changedDate,
        changedDescription);
  }

  public TaskHistoryResponse buildTaskHistoryResponse() {
    return new TaskHistoryResponse(
        id,
        task.getId(),
        name,
        project.getId(),
        assignedTo.getId(),
        createdBy.getId(),
        dueDate,
        status,
        priority,
        changedBy.getId(),
        changedDate,
        changedDescription);
  }
}
