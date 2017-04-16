package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.model.ReservableRoom;
import com.example.model.ReservableRoomId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservableRoomRepository extends JpaRepository<ReservableRoom, ReservableRoomId> {
	List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate reservedDate);	
}
