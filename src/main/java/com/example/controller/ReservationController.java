package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.ReservableRoomId;
import com.example.model.Reservation;
import com.example.model.RoleName;
import com.example.model.User;
import com.example.service.ReservationService;
import com.example.service.RoomService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("reservaions/{date}/{roomId}")
public class ReservationController {
	@Autowired
	RoomService roomService;
	@Autowired
	ReservationService reservationService;
	
	@ModelAttribute
	ReservationForm setUpForm() {
		ReservationForm form = new ReservationForm();
		//デフォルト値
		form.setStartTime(LocalTime.of(9,0));
		form.setEndTime(LocalTime.of(10,0));
		return form;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	String reserveFrom(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)@PathVariable("date")LocalDate date, @PathVariable("roomId")Integer roomId, Model model) {
		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
		List<Reservation> reservations = reservationService.findReservations(reservableRoomId);
		
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0,0), t -> t.plusMinutes(30))
									.limit(24 * 2)
									.collect(Collectors.toList());
		
		model.addAttribute("room", roomService.findRoom(roomId));
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		model.addAttribute("user", dummyUser());
		return "reserveForm";
	}
	
	private User dummyUser() {
		User user = new User();
		user.setUserId("user");
		user.setFirstName("太郎");
		user.setLastName("山田");
		user.setRoleName(RoleName.USER);
		return user;
	}
}
