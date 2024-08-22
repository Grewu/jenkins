CREATE TABLE employees
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    position      VARCHAR(100),
    department_id INT,
    email         VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255),
    FOREIGN KEY (department_id) REFERENCES departments (id)
);