-- liquibase formatted sql

--changeset nelesha:1
CREATE TABLE IF NOT EXISTS messages
(
    id           UUID PRIMARY KEY,
    user_id_from UUID      NOT NULL REFERENCES users (id),
    time         TIMESTAMP NOT NULL,
    text         VARCHAR(300)
);

--changeset nelesha:2
CREATE TABLE IF NOT EXISTS chats_types
(
    id   UUID PRIMARY KEY,
    type VARCHAR(128) UNIQUE NOT NULL
);

--changeset nelesha:3
CREATE TABLE IF NOT EXISTS chats
(
    id      UUID PRIMARY KEY,
    title   VARCHAR(128),
    type_id UUID NOT NULL REFERENCES chats_types (id)
);

--changeset nelesha:4
INSERT INTO chats_types(id, type)
VALUES (gen_random_uuid(), 'Private'),
       (gen_random_uuid(), 'Group');

--changeset nelesha:5
CREATE TABLE IF NOT EXISTS chats_messages
(
    chats_id   UUID NOT NULL REFERENCES chats (id),
    message_id UUID NOT NULL REFERENCES messages (id)
);

--changeset nelesha:6
CREATE TABLE IF NOT EXISTS chats_participant
(
    chat_id UUID NOT NULL REFERENCES chats (id),
    user_id UUID NOT NULL REFERENCES users (id)
)
