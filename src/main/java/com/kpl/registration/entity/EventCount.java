package com.kpl.registration.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_count")
public class EventCount implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = 3980366880241829960L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "event_id")
	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "rules_pdf")
	private Long reulesPdf;

	@Column(name = "owner_pdf")
	private Long ownerPdf;
	
	@Column(name = "player_pdf")
	private Long playerPdf;
}
