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
    id int unique not null primary key,
    rolename varchar(255) unique not null
);

--changeset lesha:3
create table if not exists user2role(
    userId uuid not null references users(id),
    roleId int not null references roles(id)
);