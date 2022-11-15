# TWEB_be
Backend of TWEB project

TODO: 
- Modificare Course e Professor aggiungendo il campo DELETED
- Aggiungere tabella ripetizioni (con orario, ripe effettuata / cancellata ecc)

# Query creazione DB
## Users
CREATE TABLE users ( ID VARCHAR(42) PRIMARY KEY, account VARCHAR(255) NOT NULL UNIQUE, email VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255), name VARCHAR(255), surname VARCHAR(255), role VARCHAR(255) );

## Courses
CREATE TABLE courses (ID VARCHAR(42) PRIMARY KEY , title VARCHAR(255) NOT NULL UNIQUE);

## Professors
CREATE TABLE professors (ID VARCHAR(42) PRIMARY KEY, name VARCHAR(255) NOT NULL, surname VARCHAR(255) NOT NULL);

## Teachings
CREATE TABLE teachings (
ID int NOT NULL AUTO_INCREMENT,
IDCourse VARCHAR(42) NOT NULL,
IDProfessor VARCHAR(42) NOT NULL,
PRIMARY KEY (ID),
FOREIGN KEY (IDCourse) REFERENCES courses(ID) ON DELETE CASCADE,
FOREIGN KEY (IDProfessor) REFERENCES professors(ID) ON DELETE CASCADE
);

Select * from professors where ID in (Select IDProfessor from teachings where IDCourse in ('dc4e7d4e-9d3a-4418-b0ee-b335f76518b3')) 
