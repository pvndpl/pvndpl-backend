-- liquibase formatted sql

-- changeset lesha:1
create table if not exists users (
     id uuid unique not null primary key,
     password varchar(255) unique not null,
     username varchar(255) unique not null,
     email varchar(255) unique not null,
     first_name varchar(255) not null,
     second_name varchar(255) not null
);

--changeset lesha:2
create table if not exists roles (
    id int primary key,
    rolename varchar(255) unique not null
);

--changeset lesha:3
create table if not exists user2role(
    userId uuid not null references users(id),
    roleId int not null references roles(id)
);

--changeset nelesha:4
CREATE TABLE IF NOT EXISTS messages (
    id SERIAL PRIMARY KEY,
    sending_time TIMESTAMP NOT NULL,
    text VARCHAR(255) NOT NULL
);

--changeset nelesha:5
CREATE TABLE IF NOT EXISTS user_message (
    user_id_from UUID NOT NULL REFERENCES users(id),
    user_id_to UUID NOT NULL REFERENCES users(id),
    message_id INT NOT NULL REFERENCES messages(id)
);