CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)  NOT NULL,
    project_id  BIGINT,
    assigned_to BIGINT,
    created_by  BIGINT,
    due_date    DATE          NOT NULL,
    status      status_type   NOT NULL,
    priority    priority_type NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects (id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_to) REFERENCES users_profiles (id) ON DELETE SET NULL,
    FOREIGN KEY (created_by) REFERENCES users_profiles (id) ON DELETE SET NULL
);