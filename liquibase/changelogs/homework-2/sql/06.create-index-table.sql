CREATE INDEX idx_department_id ON employees (department_id);
CREATE INDEX idx_owner_id ON projects (owner_id);
CREATE INDEX idx_project_id ON tasks (project_id);
CREATE INDEX idx_assigned_to ON tasks (assigned_to);
CREATE INDEX idx_task_id ON comments (task_id);
CREATE INDEX idx_employee_id ON comments (employee_id);