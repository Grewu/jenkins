CREATE TABLE tasks_history
(
    id                  SERIAL PRIMARY KEY,
    task_id             BIGINT        NOT NULL,
    name                VARCHAR(255)  NOT NULL,
    project_id          BIGINT        NOT NULL,
    assigned_to         BIGINT        NOT NULL,
    created_by          BIGINT        NOT NULL,
    due_date            DATE          NOT NULL,
    status              status_type   NOT NULL,
    priority            priority_type NOT NULL,
    changed_by          BIGINT        NOT NULL,
    changed_date        DATE          NOT NULL,
    changed_description TEXT          NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (assigned_to) REFERENCES users_profiles (id),
    FOREIGN KEY (changed_by) REFERENCES users_profiles (id)
);