CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    task_name   VARCHAR(255) NOT NULL,
    project_id  BIGINT,
    assigned_to BIGINT,
    due_date    DATE,
    status      VARCHAR(50),
    priority    BIGINT,
    FOREIGN KEY (project_id) REFERENCES projects (id),
    FOREIGN KEY (assigned_to) REFERENCES employees (id)
);