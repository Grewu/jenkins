CREATE TABLE comments
(
    id           SERIAL PRIMARY KEY,
    task_id      INT,
    employee_id  INT,
    comment_text TEXT NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id)
);