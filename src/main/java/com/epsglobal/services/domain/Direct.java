package com.epsglobal.services.domain;

import java.math.BigDecimal;

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
@Table(name = "directs")
public class Direct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true, length = 100)
	private String description;
	
	@Column(nullable = false, length = 50)
	private String manufacterPartNumber;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private BigDecimal cost;
	
	@Column(nullable = false)
	private BigDecimal profit;
	
	@OneToOne
	private File specifications;
	
	@OneToOne
	private Manufacter manufacter;
}
