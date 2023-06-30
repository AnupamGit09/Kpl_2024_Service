package com.kpl.registration.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
	private Long registrationId;
	private String playerFirstName;
	private String playerLastName;
	@JsonFormat(pattern = "dd.mm.yyyy hh:mm:ss")
	private ZonedDateTime registrationTime;
	 private String paymentValidation;
}