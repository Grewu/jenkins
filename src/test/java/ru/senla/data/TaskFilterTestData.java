package ru.senla.data;

import lombok.Builder;
import ru.senla.model.entity.enums.PriorityType;
import ru.senla.model.entity.enums.StatusType;
import ru.senla.model.filter.TaskFilter;

import java.time.LocalDateTime;

@Builder(setterPrefix = "with")
public class TaskFilterTestData {
    @Builder.Default
    private String name = "name";

    @Builder.Default
    private Long assignedTo = 1L;

    @Builder.Default
    private Long createdBy = 2L;

    @Builder.Default
    private Long project = 3L;

    @Builder.Default
    private LocalDateTime dueDate = LocalDateTime.parse("2024-09-30T12:00:00");

    @Builder.Default
    private StatusType status = StatusType.IN_PROGRESS;

    @Builder.Default
    private PriorityType priority = PriorityType.MEDIUM;

    public TaskFilter buildTaskFilter() {
        return new TaskFilter(name, assignedTo, createdBy, project, dueDate, status, priority);
    }
}
