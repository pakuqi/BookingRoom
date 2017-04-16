package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ReservableRoom;
import com.example.repository.ReservableRoomRepository;

@Service
@Transactional
public class ReservableRoomService {
	@Autowired
	ReservableRoomRepository reservableRoomRepository;
	
	public List<ReservableRoom> findReservableRooms(LocalDate date) {
		return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
	}
	
}
