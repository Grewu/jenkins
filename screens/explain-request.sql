EXPLAIN
SELECT *
FROM employees e
         INNER JOIN departments d ON e.department_id = d.department_id;

EXPLAIN
SELECT *
FROM projects p
         INNER JOIN employees e ON p.owner_id = e.employee_id;

EXPLAIN
SELECT *
FROM tasks t
         INNER JOIN projects p ON t.project_id = p.project_id;

EXPLAIN
SELECT *
FROM tasks t
         INNER JOIN employees e ON t.assigned_to = e.employee_id;

EXPLAIN
SELECT *
FROM comments c
         INNER JOIN tasks t ON c.task_id = t.task_id;

EXPLAIN
SELECT *
FROM comments c
         INNER JOIN employees e ON c.employee_id = e.employee_id;

