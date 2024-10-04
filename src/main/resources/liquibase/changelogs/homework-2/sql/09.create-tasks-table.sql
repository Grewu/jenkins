CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)  NOT NULL,
    project_id  BIGINT        NOT NULL,
    assigned_to BIGINT        NOT NULL,
    created_by  BIGINT        NOT NULL,
    due_date    DATE          NOT NULL,
    status      status_type   NOT NULL,
    priority    priority_type NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (assigned_to) REFERENCES users_profiles (id),
    FOREIGN KEY (created_by) REFERENCES users_profiles (id)
);