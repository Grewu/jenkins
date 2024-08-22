package model.entity;

import java.time.LocalDateTime;

public class Project {
    private final Long id;
    private final String projectName;
    private final String description;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long ownerId;

    public Project(Long id, String projectName, String description, LocalDateTime startDate, LocalDateTime endDate, Long ownerId) {
        this.id = id;
        this.projectName = projectName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }
}
