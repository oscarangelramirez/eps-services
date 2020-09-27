package com.epsglobal.services.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "carriers")
public class Carrier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true, length = 100)
	private String description;
	
	@Column(nullable = false, length = 50)
	private String manufacterPartNumber;
	
	@Column(nullable = false, length = 50)
	private String encapsulated;
	
	@Column(nullable = false)
	private Short w;
	
	@Column(nullable = false)
	private BigDecimal a0;
	
	@Column(nullable = false)
	private BigDecimal b0;
	
	@Column(nullable = false)
	private BigDecimal k0;
	
	@Column(nullable = false)
	private Integer p;
	
	@Column(nullable = false)
	private BigDecimal t;
	
	@Column(nullable = false)
	private Integer metersByReel;
	
	@Column(nullable = false)
	private Integer pocketByReel;
	
	@Column(nullable = false)
	private BigDecimal priceByReel;
	
	@Column(nullable = false)
	private BigDecimal costByReel;
	
	@Column(nullable = false)
	private BigDecimal costByMeter;
	
	@Column(nullable = false)
	private BigDecimal profit;
	
	@OneToOne
	private File drawing;
	
	@OneToOne
	private Manufacter manufacter;
	
	@OneToMany(mappedBy = "carrier")
	private List<WarehouseCarrier> warehouseCarriers;
}
