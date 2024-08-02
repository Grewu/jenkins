CREATE TABLE management.profiles (
    profile_id SERIAL PRIMARY KEY,
    employee_id INT UNIQUE,
    bio TEXT,
    profile_picture VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES management.employees(employee_id)
);