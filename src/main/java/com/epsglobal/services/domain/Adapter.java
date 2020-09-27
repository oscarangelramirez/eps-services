package com.epsglobal.services.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
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
@Table(name = "adapters")
public class Adapter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String manufacterPartNumber;
	
	@Column(nullable = false, length = 50)
	private String internalPartNumber;
	
	@Column(nullable = false, length = 50)
	private String serial;
	
	@Column(nullable = false)
	private BigDecimal cost;
	
	@Column(nullable = false)
	private Boolean isActive;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Manufacter manufacter;
}
