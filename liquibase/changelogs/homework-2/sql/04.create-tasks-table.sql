CREATE TABLE tasks
(
    id     SERIAL PRIMARY KEY,
    task_name   VARCHAR(255) NOT NULL,
    project_id  INT,
    assigned_to INT,
    due_date    DATE,
    status      VARCHAR(50),
    priority    INT,
    FOREIGN KEY (project_id) REFERENCES projects (project_id),
    FOREIGN KEY (assigned_to) REFERENCES employees (employee_id)
);
