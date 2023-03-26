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
ID VARCHAR(42) PRIMARY KEY,
IDCourse VARCHAR(42) NOT NULL,
IDProfessor VARCHAR(42) NOT NULL,
FOREIGN KEY (IDCourse) REFERENCES courses(ID) ON DELETE CASCADE,
FOREIGN KEY (IDProfessor) REFERENCES professors(ID) ON DELETE CASCADE
);

## Repetitions
CREATE TABLE repetitions (
ID VARCHAR(42) PRIMARY KEY,
IDCourse VARCHAR(42) NOT NULL,
IDProfessor VARCHAR(42) NOT NULL,
IDUser VARCHAR(42) NOT NULL,
date DATE NOT NULL,
time int NOT NULL CHECK (time >= 0 AND time <= 23),
status VARCHAR(255) NOT NULL check (status = 'done' OR status = 'pending' OR status = 'deleted'),
note TEXT,
FOREIGN KEY (IDCourse) REFERENCES courses(ID) ON DELETE CASCADE,
FOREIGN KEY (IDProfessor) REFERENCES professors(ID) ON DELETE CASCADE,
FOREIGN KEY (IDUser) REFERENCES users(ID) ON DELETE CASCADE
);


## Add data
INSERT INTO users (ID, account, email, password, name, surname, role) 
VALUES 
('1', 'giovanni', 'giovanni@example.com', SHA2('pass',256), 'Giovanni', 'Bianchi', 'User'), 
('2', 'maria', 'maria@example.com', SHA2('pass',256), 'Maria', 'Rossi', 'User'),
('3', 'carlo', 'carlo@example.com', SHA2('pass',256), 'Carlo', 'Verdi', 'User'), ('4', 'lucia', 'lucia@example.com', SHA2('pass',256), 'Lucia', 'Neri', 'User'), ('5', 'matteo', 'matteo@example.com', SHA2('pass',256), 'Matteo', 'Bianchi', 'User'), ('6', 'francesca', 'francesca@example.com', SHA2('pass',256), 'Francesca', 'Rossi', 'User'), ('7', 'luca', 'luca@example.com', SHA2('pass',256), 'Luca', 'Verdi', 'User'), ('8', 'giorgia', 'giorgia@example.com', SHA2('pass',256), 'Giorgia', 'Neri', 'User'), ('9', 'emme', 'emmedeveloper@gmail.com', SHA2('pass',256), 'Marco', 'Molica', 'Admin');

INSERT INTO courses (ID, title)
VALUES
('1', 'Programmazione 1'),
('2', 'Algoritmi'),
('3', 'Database');

INSERT INTO professors (ID, name, surname)
VALUES
('1', 'Giovanni', 'Rossi'),
('2', 'Maria', 'Bianchi'),
('3', 'Carlo', 'Neri'),
('4', 'Lucia', 'Verdi'),
('5', 'Matteo', 'Grigi');

