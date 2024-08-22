CREATE TABLE projects
(
    id           SERIAL PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description  TEXT,
    start_date   DATE,
    end_date     DATE,
    owner_id     BIGINT,
    FOREIGN KEY (owner_id) REFERENCES employees (id)
);