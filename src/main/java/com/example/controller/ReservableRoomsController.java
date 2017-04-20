package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.service.ReservableRoomService;
import com.example.model.ReservableRoom;

@Controller
@RequestMapping("reservablerooms")
public class ReservableRoomsController {
	@Autowired
	ReservableRoomService reservableRoomService;
	
	@RequestMapping(method = RequestMethod.GET)
	String listRooms(Model model) {
		LocalDate today = LocalDate.now();
		List<ReservableRoom> reservableRooms = reservableRoomService.findReservableRooms(today);
		model.addAttribute("date", today);
		model.addAttribute("reservablerooms", reservableRooms);	
		return "listReservableRooms";
		
	}
	
	@RequestMapping(path ="{date}", method = RequestMethod.GET)
	String listRooms(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
		List<ReservableRoom> rooms = reservableRoomService.findReservableRooms(date);
		model.addAttribute("reservablerooms", rooms);
		return "listReservableRooms";
	}


}
