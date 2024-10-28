CREATE TABLE tasks_history
(
    id                  SERIAL PRIMARY KEY,
    task_id             BIGINT,
    name                VARCHAR(255)  NOT NULL,
    project_id          BIGINT,
    assigned_to         BIGINT,
    created_by          BIGINT,
    due_date            TIMESTAMP     NOT NULL,
    status              status_type   NOT NULL,
    priority            priority_type NOT NULL,
    changed_by          BIGINT        NOT NULL,
    changed_date        TIMESTAMP     NOT NULL,
    changed_description TEXT          NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE SET NULL,
    FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_to) REFERENCES users_profiles (id) ON DELETE SET NULL,
    FOREIGN KEY (changed_by) REFERENCES users_profiles (id) ON DELETE SET NULL
);