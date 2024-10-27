CREATE INDEX idx_users_profiles_department_id ON users_profiles (department_id);
CREATE INDEX idx_users_profiles_position_id ON users_profiles (position_id);


CREATE INDEX idx_projects_owner_id ON projects (owner_id);
CREATE INDEX idx_projects_project_code ON projects (project_code);


CREATE INDEX idx_tasks_project_id ON tasks (project_id);
CREATE INDEX idx_tasks_assigned_to ON tasks (assigned_to);
CREATE INDEX idx_tasks_status ON tasks (status);
CREATE INDEX idx_tasks_priority ON tasks (priority);


CREATE INDEX idx_tasks_history_task_id ON tasks_history (task_id);
CREATE INDEX idx_tasks_history_changed_date ON tasks_history (changed_date);
CREATE INDEX idx_tasks_history_changed_by ON tasks_history (changed_by);

CREATE INDEX idx_comments_task_id ON comments (task_id);
CREATE INDEX idx_comments_user_profile_id ON comments (user_profile_id);


CREATE INDEX idx_users_email ON users (email);

CREATE INDEX idx_role_privilege_role_id ON role_privilege (role_id);
CREATE INDEX idx_role_privilege_privilege_id ON role_privilege (privilege_id);


