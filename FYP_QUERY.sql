CREATE DATABASE fetalhealthanalysis;
USE fetalhealthanalysis;

CREATE TABLE RegisteredUsers (
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20)
);