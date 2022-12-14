-- liquibase formatted sql

--changeset kottragu:1
create table tags(
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    sysname VARCHAR(255) NOT NULL UNIQUE
);

--changeset kottragu:2
create table user2tag(
    userId UUID NOT NULL,
    tagId UUID NOT NULL
)