package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Room;
import com.example.repository.RoomRepository;

@Service
@Transactional
public class RoomService {
	@Autowired
	RoomRepository roomRepository;
	
	public Room findRoom(Integer roomId) {
		return roomRepository.findOne(roomId);
	}
}
