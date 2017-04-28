package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.form.ReservationForm;
import com.example.model.ReservableRoom;
import com.example.model.ReservableRoomId;
import com.example.model.Reservation;
import com.example.model.User;
import com.example.service.ReservationService;
import com.example.service.ReservationUserDetails;
import com.example.service.RoomService;
import com.example.service.exception.AlreadyReservedException;
import com.example.service.exception.UnavailableReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
public class ReservationsController {
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
	String reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, 
						@AuthenticationPrincipal ReservationUserDetails userDetails,
						@PathVariable("roomId")Integer roomId, Model model) {
		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
		List<Reservation> reservations = reservationService.findReservations(reservableRoomId);
		
		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0,0), t -> t.plusMinutes(30))
									.limit(24 * 2)
									.collect(Collectors.toList());
		
		model.addAttribute("date", date);
		model.addAttribute("room", roomService.findRoom(roomId));
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		model.addAttribute("user", userDetails.getUser());
		return "reserveForm";
	}
		
	@RequestMapping(method = RequestMethod.POST)
	String reserve(@Validated ReservationForm form, BindingResult bindingResult,
					@AuthenticationPrincipal ReservationUserDetails userDetails,
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
					@PathVariable("roomId") Integer roomId, Model model) {
		
		if (bindingResult.hasErrors()) {
			return reserveForm(date, userDetails,roomId, model);
		}
		
		ReservableRoom reservableRoom = new ReservableRoom(new ReservableRoomId(roomId, date));
		Reservation reservation = new Reservation();
		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setReservableRoom(reservableRoom);
		reservation.setUser(userDetails.getUser());
		
		try {
			reservationService.reserve(reservation);
		}
		catch (UnavailableReservationException | AlreadyReservedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, userDetails, roomId, model);
		}
		return "redirect:/reservations/{date}/{roomId}";
	}
	
	@RequestMapping(method = RequestMethod.POST, params = "cancel")
	String cancel(@AuthenticationPrincipal ReservationUserDetails userDetails,
					@RequestParam("reservationId") Integer reservationId,
					@PathVariable("roomId") Integer roomId,
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, 
					Model model) {
		User user = userDetails.getUser();
		try {
			reservationService.cancel(reservationId, user);
		}
		catch (AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, userDetails,roomId, model);
		}
		return "redirect:/reservations/{date}/{roomId}";
	}
}
