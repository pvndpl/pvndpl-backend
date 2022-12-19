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
);

--changeset max:3
INSERT INTO tags(id, name, sysname)
VALUES (gen_random_uuid(), 'TV', 'TV'),
       (gen_random_uuid(), 'Music', 'Music'),
       (gen_random_uuid(), 'Movies', 'Movies'),
       (gen_random_uuid(), 'Books', 'Books'),
       (gen_random_uuid(), 'Games', 'Games'),
       (gen_random_uuid(), 'Girls', 'Girls'),
       (gen_random_uuid(), 'Beer', 'Beer'),
       (gen_random_uuid(), 'Football', 'Football'),
       (gen_random_uuid(), 'Porn', 'Porn');
