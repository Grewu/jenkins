CREATE TABLE comments
(
    id              SERIAL PRIMARY KEY,
    task_id         BIGINT NOT NULL,
    user_profile_id BIGINT NOT NULL,
    comment_text    TEXT   NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (user_profile_id) REFERENCES users_profiles (id)
);