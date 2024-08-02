CREATE TABLE management.employee_project (
    employee_id INT,
    project_id INT,
    role VARCHAR(100),
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES management.employees(employee_id),
    FOREIGN KEY (project_id) REFERENCES management.projects(project_id)
);