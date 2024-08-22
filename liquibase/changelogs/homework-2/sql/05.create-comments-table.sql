CREATE TABLE comments
(
    id           SERIAL PRIMARY KEY,
    task_id      BIGINT,
    employee_id  BIGINT,
    comment_text TEXT NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id)
);