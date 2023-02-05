# TWEB_be
Backend of TWEB project

TODO:

# Query creazione DB
## Users
CREATE TABLE users ( ID VARCHAR(42) PRIMARY KEY, account VARCHAR(255) NOT NULL UNIQUE, email VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255), name VARCHAR(255), surname VARCHAR(255), role VARCHAR(255));

## Courses
CREATE TABLE courses (ID VARCHAR(42) PRIMARY KEY , title VARCHAR(255) NOT NULL UNIQUE, deleted BOOLEAN NOT NULL DEFAULT FALSE);

## Professors
CREATE TABLE professors (ID VARCHAR(42) PRIMARY KEY, name VARCHAR(255) NOT NULL, surname VARCHAR(255) NOT NULL, deleted BOOLEAN NOT NULL DEFAULT FALSE);

## Teachings
CREATE TABLE teachings (
ID int NOT NULL AUTO_INCREMENT,
IDCourse VARCHAR(42) NOT NULL,
IDProfessor VARCHAR(42) NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (IDCourse) REFERENCES courses(ID) ON DELETE CASCADE,
FOREIGN KEY (IDProfessor) REFERENCES professors(ID) ON DELETE CASCADE
);

## Repetitions
CREATE TABLE repetitions (
ID int NOT NULL AUTO_INCREMENT,
IDCourse VARCHAR(42) NOT NULL,
IDProfessor VARCHAR(42) NOT NULL,
IDUser VARCHAR(42) NOT NULL,
date DATE NOT NULL,
time int NOT NULL CHECK (time >= 0 AND time <= 23),
status VARCHAR(255) NOT NULL check (status = 'done' OR status = 'pending' OR status = 'deleted'),
note TEXT,
PRIMARY KEY (ID),
FOREIGN KEY (IDCourse) REFERENCES courses(ID) ON DELETE CASCADE,
FOREIGN KEY (IDProfessor) REFERENCES professors(ID) ON DELETE CASCADE,
FOREIGN KEY (IDUser) REFERENCES users(ID) ON DELETE CASCADE
);


## Add data
INSERT INTO users (ID, account, email, password, name, surname, role)
VALUES
('8a8b2c5f-6a1c-4f9e-9d96-e547f9a6a8d8', 'giovanni', 'giovanni@example.com', SHA2('pass',256), 'Giovanni', 'Bianchi', 'User"),
('5f5c4d3f-4a1d-3f9e-9d96-f547f9a6a8d8', 'maria', 'maria@example.com', SHA2('pass',256), 'Maria', 'Rossi', 'User"),
('6a8b2c5f-6a1c-4f9e-9d96-e547f9a6a8d8', 'carlo', 'carlo@example.com', SHA2('pass',256), 'Carlo', 'Verdi', 'User"),
('9c8b2c5f-6a1c-4f9e-9d96-e547f9a6a8d8', 'lucia', 'lucia@example.com', SHA2('pass',256), 'Lucia', 'Neri', 'User"),
('7a8b2c5f-6a1c-4f9e-9d96-e547f9a6a8d8', 'matteo', 'matteo@example.com', SHA2('pass',256), 'Matteo', 'Bianchi', 'User"),
('8a8b2c6f-6a1c-4f9e-9d96-e547f9a6a8d8', 'francesca', 'francesca@example.com', SHA2('pass',256), 'Francesca', 'Rossi', 'User"),
('8a8b2c7f-6a1c-4f9e-9d96-e547f9a6a8d8', 'luca', 'luca@example.com', SHA2('pass',256), 'Luca', 'Verdi', 'User"),
('8a8b2c8f-6a1c-4f9e-9d96-e547f9a6a8d8', 'giorgia', 'giorgia@example.com', SHA2('pass',256), 'Giorgia', 'Neri', 'User"),
('8a8b2c8f-6a1c-4f9e-9d96-e547f9a6a8d8', 'emme', 'emmedeveloper@gmail.com', SHA2('pass',256), 'Marco', 'Molica', 'Admin");

INSERT INTO courses (ID, title)
VALUES
('8a8b1c5a-6a1c-4f9e-9d96-e547f9a6a8d8', 'Programmazione 1'),
('8a8b2c5a-6a1c-4f9e-9d96-e547f9a6a8d8', 'Algoritmi'),
('8a8b3c5a-6a1c-4f9e-9d96-e547f9a6a8d8', 'Database');

INSERT INTO professors (ID, name, surname)
VALUES
('8a8b2c1b-6a1c-4f9e-9d96-e547f9a6a8d8', 'Giovanni', 'Rossi'),
('8a8b2c2b-6a1c-4f9e-9d96-e547f9a6a8d8', 'Maria', 'Bianchi'),
('8a8b2c3b-6a1c-4f9e-9d96-e547f9a6a8d8', 'Carlo', 'Neri'),
('8a8b2c4b-6a1c-4f9e-9d96-e547f9a6a8d8', 'Lucia', 'Verdi'),
('8a8b2c5b-6a1c-4f9e-9d96-e547f9a6a8d8', 'Matteo', 'Grigi');

INSERT INTO teachings (IDCourse, IDProfessor)
VALUES
('8a8b1c5a-6a1c-4f9e-9d96-e547f9a6a8d8', '8a8b2c1b-6a1c-4f9e-9d96-e547f9a6a8d8'),
('8a8b1c5a-6a1c-4f9e-9d96-e547f9a6a8d8', '8a8b2c2b-6a1c-4f9e-9d96-e547f9a6a8d8'),
('8a8b2c5a-6a1c-4f9e-9d96-e547f9a6a8d8', '8a8b2c3b-6a1c-4f9e-9d96-e547f9a6a8d8'),
('8a8b2c5a-6a1c-4f9e-9d96-e547f9a6a8d8', '8a8b2c4b-6a1c-4f9e-9d96-e547f9a6a8d8'),
('8a8b3c5a-6a1c-4f9e-9d96-e547f9a6a8d8', '8a8b2c5b-6a1c-4f9e-9d96-e547f9a6a8d8');

