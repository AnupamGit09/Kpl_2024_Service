package com.kpl.registration.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doc_info")
public class DocInfo implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = 3980366880241829960L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "doc_id")
	@Column(name = "doc_id")
	private Long docId;

	@NonNull
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] docImageFront;

	@NonNull
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] docImageBack;

	@NonNull
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] image;

	@Column(name = "registration_id")
	private Long registrationId;

}
