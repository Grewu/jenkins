CREATE TABLE users_profiles
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    position_id   BIGINT       NOT NULL,
    department_id BIGINT       NOT NULL,
    user_id       BIGINT       NOT NULL,
    FOREIGN KEY (position_id) REFERENCES positions (id),
    FOREIGN KEY (department_id) REFERENCES departments (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);