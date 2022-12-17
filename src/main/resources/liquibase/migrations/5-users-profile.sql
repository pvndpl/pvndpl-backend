-- liquibase formatted sql

--changeset max:1
CREATE TABLE IF NOT EXISTS social_networks
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(128) NOT NULL UNIQUE
);

--changeset max:2
CREATE TABLE IF NOT EXISTS users_profiles
(
    user_id UUID PRIMARY KEY REFERENCES users (id),
    posts_count INT NOT NULL,
    subscribers_count INT NOT NULL,
    subscriptions_count INT NOT NULL,
    about VARCHAR(255),
    created_date DATE NOT NULL,
    city VARCHAR(128),
    website VARCHAR(255),
    tv_shows VARCHAR(255),
    showmen VARCHAR(255),
    books VARCHAR(255),
    games VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    birthdate DATE,
    busyness VARCHAR(255),
    native_city VARCHAR(255)
);

--changeset max:3
CREATE TABLE IF NOT EXISTS profiles_socials_networks
(
    profile_id UUID NOT NULL REFERENCES users_profiles (user_id),
    social_id INT NOT NULL REFERENCES social_networks (id),
    url VARCHAR(255) NOT NULL,
    CONSTRAINT uniq UNIQUE (profile_id, social_id)
);