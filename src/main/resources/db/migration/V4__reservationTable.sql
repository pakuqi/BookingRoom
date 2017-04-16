-- reservation Table

CREATE TABLE reservation (
	reservation_id	SERIAL PRIMARY KEY,
	start_time		TIME NOT NULL,
	end_time		TIME NOT NULL,
	reserved_date	DATE NOT NULL,
	room_id			INT4 NOT NULL,
	user_id			VARCHAR(255) NOT NULL
);