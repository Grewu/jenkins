CREATE TABLE users_profiles
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    position_id   BIGINT       NOT NULL,
    department_id BIGINT,
    user_id       BIGINT       NOT NULL,
    FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE SET NULL,
    FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);