package com.epsglobal.services.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "devices")
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String customer;
	
	@Column(nullable = false, length = 50)
	private String manufacterPartNumber;
	
	@Column(nullable = false, length = 50)
	private String blankPartNumber;
	
	@OneToOne
	private Manufacter manufacter;
}
