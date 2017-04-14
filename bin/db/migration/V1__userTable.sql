-- User Table

CREATE TABLE mst_user (
	id SERIAL PRIMARY KEY,
	email CHAR varying(20) NOT NULL,
	name CHAR varying(40) NOT NULL,
	password CHAR varying(70) NOT NULL,
	UNIQUE (email)
);