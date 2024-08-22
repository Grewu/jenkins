INSERT INTO departments (department_name)
VALUES ('Human Resources'),
       ('Engineering'),
       ('Sales'),
       ('Marketing');

INSERT INTO employees (first_name, last_name, position, department_id, email, password_hash)
VALUES ('Alice', 'Smith', 'Manager', 1, 'alice.smith@example.com', 'password_hash'),
       ('Bob', 'Johnson', 'Developer', 2, 'bob.johnson@example.com', 'password_hash'),
       ('Charlie', 'Williams', 'Salesperson', 3, 'charlie.williams@example.com', 'password_hash'),
       ('David', 'Brown', 'Marketing Specialist', 4, 'david.brown@example.com', 'password_hash');

INSERT INTO projects (project_name, description, start_date, end_date, owner_id)
VALUES ('Project Alpha', 'A project for developing Alpha features.', '2024-01-01', '2024-12-31', 1),
       ('Project Beta', 'A project for developing Beta features.', '2024-02-01', '2024-11-30', 2);

INSERT INTO tasks (task_name, project_id, assigned_to, due_date, status, priority)
VALUES ('Design Database Schema', 1, 2, '2024-03-01', 'In Progress', 1),
       ('Develop API Endpoints', 1, 2, '2024-06-01', 'Not Started', 2),
       ('Create Marketing Plan', 2, 4, '2024-04-01', 'In Progress', 1),
       ('Conduct Sales Training', 2, 3, '2024-05-01', 'Completed', 3);

INSERT INTO comments (task_id, employee_id, comment_text, created_at)
VALUES (1, 2, 'Initial schema design completed.', '2024-01-15 10:00:00'),
       (1, 2, 'Reviewed schema with the team.', '2024-01-20 14:00:00'),
       (2, 2, 'Started working on API endpoints.', '2024-03-01 09:00:00'),
       (3, 4, 'Draft marketing plan ready for review.', '2024-02-15 11:30:00');