ALTER TABLE reservable_room ADD CONSTRAINT FK_reservable_room_room_id FOREIGN KEY (room_id) REFERENCES room;
ALTER TABLE reservation ADD CONSTRAINT FK_reservation_reservable FOREIGN KEY (reserved_date, room_id) REFERENCES reservable_room;
ALTER TABLE reservation ADD CONSTRAINT FK_reservation_user_id FOREIGN KEY (user_id) REFERENCES mst_user;