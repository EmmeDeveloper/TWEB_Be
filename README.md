# TWEB_be
Backend of TWEB project

# Query creazione DB
## Users
CREATE TABLE users ( ID BINARY(36) PRIMARY KEY, account VARCHAR(255) NOT NULL UNIQUE, email VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255), name VARCHAR(255), surname VARCHAR(255), role VARCHAR(255) );

