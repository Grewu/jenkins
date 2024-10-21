CREATE TABLE comments
(
    id              SERIAL PRIMARY KEY,
    task_id         BIGINT,
    user_profile_id BIGINT,
    comment_text    TEXT   NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE SET NULL,
    FOREIGN KEY (user_profile_id) REFERENCES users_profiles (id) ON DELETE SET NULL
);