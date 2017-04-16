-- User Table

CREATE TABLE mst_user (
	user_id		VARCHAR(255) PRIMARY KEY,
	first_name	VARCHAR(255) NOT NULL,
	last_name	VARCHAR(255) NOT NULL,
	password	VARCHAR(255) NOT NULL,
	role_name	VARCHAR(255) NOT NULL
);