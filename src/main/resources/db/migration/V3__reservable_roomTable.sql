-- Room Table

CREATE TABLE reservable_room (
	reserved_date	DATE NOT NULL,
	room_id			INT4 NOT NULL,
	PRIMARY KEY (reserved_date, room_id)
);