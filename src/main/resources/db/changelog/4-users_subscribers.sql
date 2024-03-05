-- liquibase formatted sql

--changeset max:1
CREATE TABLE IF NOT EXISTS users_subscribers
(
    user_id        UUID NOT NULL REFERENCES users (id),
    subscribers_id UUID NOT NULL REFERENCES users (id),
    CONSTRAINT primary_key PRIMARY KEY (user_id, subscribers_id)
);
