CREATE TABLE projects
(
    id           SERIAL PRIMARY KEY,
    project_code VARCHAR(30)  NOT NULL,
    name         VARCHAR(255) NOT NULL,
    description  TEXT         NOT NULL,
    start_date   TIMESTAMP    NOT NULL,
    end_date     TIMESTAMP    NOT NULL,
    owner_id     BIGINT,
    FOREIGN KEY (owner_id) REFERENCES users_profiles (id) ON DELETE SET NULL
);