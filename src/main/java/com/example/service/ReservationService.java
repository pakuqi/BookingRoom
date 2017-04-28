package com.example.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.ReservableRoom;
import com.example.model.ReservableRoomId;
import com.example.model.Reservation;
import com.example.model.RoleName;
import com.example.model.User;
import com.example.repository.ReservableRoomRepository;
import com.example.repository.ReservationRepository;
import com.example.service.exception.AlreadyReservedException;
import com.example.service.exception.UnavailableReservationException;

@Service
@Transactional
public class ReservationService {
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	ReservableRoomRepository reservableRoomRepository;
	
	public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
		return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
	}
	
	public Reservation reserve(Reservation reservation) {
		ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();
		//対象の部屋が予約可能かどうかチェック
		//悲観ロック
		ReservableRoom reservable = reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId);
		if (reservable == null){
			//例外スロー
			throw new UnavailableReservationException("入力の日付・部屋の組み合わせは予約できません");
		}
		//重複チェック
		boolean overlap = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
				.stream()
				.anyMatch(x -> x.overlap(reservation));
		
		if (overlap) {
			//例外スロー
			//例外スロー
			throw new AlreadyReservedException("入力の時間帯は既に予約済みです");
		}
		
		//予約情報の登録
		reservationRepository.save(reservation);
		return reservation;
	}
	
	public void cancel(Integer reservationId, User requestUser) {
		Reservation reservation = reservationRepository.findOne(reservationId);
		
		if (RoleName.ADMIN != requestUser.getRoleName() && !Objects.equals(reservation.getUser().getUserId(), requestUser.getUserId())) {
			throw new AccessDeniedException("要求されたキャンセルは許可できません");
		}
		reservationRepository.delete(reservationId);
	}
}
