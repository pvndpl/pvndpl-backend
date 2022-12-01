-- liquibase formatted sql

--changeset tolya:1
create table if not exists posts(
    id uuid unique not null primary key,
    creator_id uuid not null references users(id) ,
    category varchar(255) not null,
    content varchar(255) not null
)