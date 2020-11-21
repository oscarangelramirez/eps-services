package com.epsglobal.services.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "warehouses_carriers_outputs")
public class WarehouseCarrierOutput {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = true, length = 15)
	private String order;
	
	@Column(nullable = true, length = 1000)
	private String comments;
	
	@Column(nullable = true)
	private Integer complete;
	
	@Column(nullable = true)
	private Integer partial;
	
	@Column(nullable = false)
	private BigDecimal priceByReel;
	
	@Column(nullable = false)
	private BigDecimal costByReel;
	
	@Column(nullable = false)
	private BigDecimal costByMeter;
	
	@Column(nullable = false)
	private BigDecimal profit;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_carrier_id")
	private WarehouseCarrier warehouseCarrier;
	
	@OneToOne
	private User user;
}