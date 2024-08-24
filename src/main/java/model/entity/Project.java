package model.entity;

import java.time.LocalDateTime;

public class Project {
    private final Long id;
    private final String projectName;
    private final String description;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Long ownerId;


    public Project(Project.Builder builder) {
        this.id = builder.id;
        this.projectName = builder.projectName;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.ownerId = builder.ownerId;
    }

    public static class Builder {
        private Long id;
        private String projectName;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Long ownerId;

        public Builder() {
        }

        public Project.Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Project.Builder setProjectName(String projectName) {
            this.projectName = projectName;
            return this;
        }

        public Project.Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Project.Builder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Project.Builder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Project.Builder setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Project build() {
            return new Project(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
