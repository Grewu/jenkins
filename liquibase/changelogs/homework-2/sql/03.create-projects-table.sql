CREATE TABLE management.projects (
    project_id SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES management.employees(employee_id)
);