INSERT INTO roles (id, name)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'GUEST');

INSERT INTO privileges (id, name)
VALUES (1, 'READ'),
       (2, 'WRITE'),
       (3, 'DELETE');

INSERT INTO role_privilege (role_id, privilege_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (3, 1);

INSERT INTO departments (id, name)
VALUES (1, 'DEVELOPERS'),
       (2, 'MANAGERS'),
       (3, 'MARKETING'),
       (4, 'HR');

INSERT INTO positions (id, name)
VALUES (1, 'MANAGER'),
       (2, 'DEVELOPER'),
       (3, 'MARKETING_SPECIALIST'),
       (4, 'HR');

INSERT INTO users (email, password, role_id)
VALUES ('admin@example.com', '$2a$10$BS7udr3QYwyUkX4w3p2V.Ovjc/kWnjMJMrSTu9.XFzrHbWuDLasjK', 1),
       ('user@example.com', '$2a$10$PuhG.KXSWyD0CAPJf1abgu8EyCdCa0/Q0EZYdUdN9PaCOFzydgCAW', 2),
       ('guest@example.com', '$2a$10$lpvBiZ9Vp/caYDQBSpeItuxglzf/Ukx4LRnTTEjw4mmR.iG0.Y67u', 3),
       ('anotheruser@example.com', '$2a$10$kSsePSg1z1uGyt14p97Bp.VmN1mkCpuV2Yykiv4IUI6RJqVIt4que', 4);

INSERT INTO users_profiles (first_name, last_name, position_id, department_id, user_id)
VALUES ('Alice', 'Smith', 2, 1, 1),
       ('Bob', 'Johnson', 1, 2, 2),
       ('Charlie', 'Williams', 3, 3, 3),
       ('David', 'Brown', 4, 4, 4);

INSERT INTO projects (project_code, name, description, start_date, end_date, owner_id)
VALUES ('PROJ001', 'Web Application Development',
        'A project focused on developing a multi-functional web application for task management.', '2024-09-01',
        '2024-12-31', 1),
       ('PROJ002', 'Database Migration', 'A project aimed at migrating the existing database to a new platform.',
        '2024-10-01', '2025-01-15', 2),
       ('PROJ003', 'Security System Upgrade', 'A project to implement new security measures in the existing system.',
        '2024-11-01', '2025-02-28', 3);

INSERT INTO tasks (name, project_id, assigned_to, created_by, due_date, status, priority)
VALUES ('Design Database Schema', 1, 2, 1, '2024-03-01', 'IN_PROGRESS', 'HIGH'),
       ('Develop API Endpoints', 1, 2, 1, '2024-06-01', 'NOT_STARTED', 'MEDIUM'),
       ('Create Marketing Plan', 2, 4, 2, '2024-04-01', 'IN_PROGRESS', 'LOW'),
       ('Conduct Sales Training', 2, 3, 3, '2024-05-01', 'COMPLETED', 'HIGH');

INSERT INTO tasks_history (task_id, name, project_id, assigned_to, created_by, due_date, status, priority, changed_by,
                           changed_date, changed_description)
VALUES (1, 'Design Login Page', 1, 2, 1, '2024-09-15', 'IN_PROGRESS', 'HIGH', 2, '2024-09-12',
        'Initial task creation and assignment.'),
       (2, 'Database Schema Update', 2, 3, 2, '2024-10-20', 'NOT_STARTED', 'MEDIUM', 3, '2024-10-01',
        'Task created for database migration project.'),
       (3, 'Security Audit', 3, 4, 3, '2024-11-25', 'COMPLETED', 'LOW', 4, '2024-11-10',
        'Security audit scheduled to enhance system security.');


INSERT INTO comments (task_id, user_profile_id, comment_text, created_at)
VALUES (1, 2, 'Initial schema design completed.', '2024-01-15 10:00:00'),
       (1, 2, 'Reviewed schema with the team.', '2024-01-20 14:00:00'),
       (2, 2, 'Started working on API endpoints.', '2024-03-01 09:00:00'),
       (3, 4, 'Draft marketing plan ready for review.', '2024-02-15 11:30:00');

