package com.example.form;

import java.io.Serializable;
import java.time.LocalTime;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.util.EndTimeMustBeAfterStartTime;
import com.example.util.ThirtyMinutesUnit;

import lombok.Data;

@Data
@EndTimeMustBeAfterStartTime(message = "終了時間は開始時間より後にして下さい")
public class ReservationForm implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "必須です")
	@ThirtyMinutesUnit(message = " 30分単位で入力して下さい")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;
	
	@NotNull(message = "必須です")
	@ThirtyMinutesUnit(message = " 30分単位で入力して下さい")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;
	
}
