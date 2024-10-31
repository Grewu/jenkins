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
VALUES ('pavelgurevichwork@gmail.com', 'password', 1),
       ('pavelgurevich97@gmail.com', 'password', 2),
       ('guest@example.com', 'password', 3),
       ('anotheruser@example.com', 'password', 4);

INSERT INTO users_profiles (first_name, last_name, position_id, department_id, user_id)
VALUES ('firstName', 'lastName', 1, 1, 1),
       ('firstName', 'lastName', 1, 1, 1),
       ('firstName', 'lastName', 1, 1, 1),
       ('firstName', 'lastName', 1, 1, 1);

INSERT INTO projects (project_code, name, description, start_date, end_date, owner_id)
VALUES ('projectCode', 'name', 'description', '2024-09-30T12:00:00', '2024-09-30T12:00:00', 1),
       ('projectCode', 'name', 'description', '2024-09-30T12:00:00', '2024-09-30T12:00:00', 1),
       ('projectCode', 'name', 'description', '2024-09-30T12:00:00', '2024-09-30T12:00:00', 1);

INSERT INTO tasks (name, project_id, assigned_to, created_by, due_date, status, priority)
VALUES ('name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM'),
       ('name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM'),
       ('name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM'),
       ('name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM');

INSERT INTO tasks_history (task_id, name, project_id, assigned_to, created_by, due_date, status, priority, changed_by,
                           changed_date, changed_description)
VALUES (1, 'name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM', 1, '2024-09-30T12:00:00',
        'changedDescription'),
       (1, 'name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM', 1, '2024-09-30T12:00:00',
        'changedDescription'),
       (1, 'name', 1, 1, 1, '2024-09-30T12:00:00', 'IN_PROGRESS', 'MEDIUM', 1, '2024-09-30T12:00:00',
        'changedDescription');


INSERT INTO comments (task_id, user_profile_id, comment_text, created_at)
VALUES (1, 1, 'commentText', '2024-09-30T12:00:00'),
       (1, 1, 'commentText', '2024-09-30T12:00:00'),
       (1, 1, 'commentText', '2024-09-30T12:00:00'),
       (1, 1, 'commentText', '2024-09-30T12:00:00');

