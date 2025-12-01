package com.kpl.registration.dto.AllOther;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseVO {
	private String playerFirstName;
	private String playerLastName;
	private String playerAddress;
	private Long aadharNo;
	private Long phNo;
	private String generue;
	private String location;
}